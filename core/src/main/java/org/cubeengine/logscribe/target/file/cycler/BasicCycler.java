package org.cubeengine.logscribe.target.file.cycler;

import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.IdentityHashMap;
import java.util.Map;
import org.cubeengine.logscribe.MacroProcessor;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;

public abstract class BasicCycler implements LogCycler
{
    private static final String MACRO_DATE = "date";
    private static final String MACRO_NAME = "name";
    private static final String MACRO_ENDING = "ending";
    private static final String MACRO_INDEX = "i";
    private static final String MACRO_INDEX_SUFFIX = "_i";

    public static final String DEFAULT_LINE_FORMAT = "{" + MACRO_NAME + "}_{" + MACRO_DATE + "}{" + MACRO_INDEX_SUFFIX + "}{" + MACRO_ENDING + "}";
    public static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd--HHmm");

    private String format;
    private DateTimeFormatter dateFormat;

    public BasicCycler(String format, DateTimeFormatter dateFormat)
    {
        this.format = format;
        this.dateFormat = dateFormat;
    }

    @Override
    public Path cycle(Path path, CloseCallback closeCallBack)
    {
        // Close open stream
        closeCallBack.close();
        Path directory = path.getParent();
        String name = path.getFileName().toString();
        String ending = "";
        if (name.contains("."))
        {
            ending = name.substring(name.lastIndexOf('.'));
            name = name.substring(0, name.lastIndexOf('.'));
        }
        Map<String, Object> map = new IdentityHashMap<>(5);
        map.put(MACRO_DATE, dateFormat.format(Instant.now()));
        map.put(MACRO_NAME, name);
        map.put(MACRO_ENDING, ending);
        map.put(MACRO_INDEX, "");
        map.put(MACRO_INDEX_SUFFIX, "");

        Path cycled;
        int i = 1;
        do
        {
            cycled = directory.resolve(MacroProcessor.processMacros(format, map));
            map.put(MACRO_INDEX_SUFFIX, "_" + i);
            map.put(MACRO_INDEX, i++);
        }
        while (Files.exists(cycled));

        Path cycledParent = cycled.getParent();
        if (!Files.exists(cycledParent))
        {
            try
            {
                Files.createDirectories(cycledParent);
            }
            catch (IOException e)
            {
                throw new LogCyclerException("Could not create the parent-folder for file to cycle", e);
            }
        }

        try
        {
            move(path, cycled);
        }
        catch (IOException e)
        {
            throw new LogCyclerException("Error when Cycling", e);
        }

        return path;
    }

    private static void move(Path from, Path to) throws IOException
    {
        try
        {
            Files.move(from, to, ATOMIC_MOVE);
        }
        catch (AtomicMoveNotSupportedException ignored)
        {
            Files.move(from, to);
        }
    }
}
