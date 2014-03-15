package de.cubeisland.engine.logging.target.file.cycler;

import de.cubeisland.engine.logging.LoggingException;
import de.cubeisland.engine.logging.MacroProcessor;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Logcycling based on FileSize
 */
public class FilesizeCycler implements LogCycler
{
    private static final MacroProcessor MACRO_PROCESSOR = new MacroProcessor();
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd--HHmm";

    // {i} {date} {name} {ending}

    private long bytes;
    private String format;
    private DateFormat dateFormat;

    public FilesizeCycler(long bytes)
    {
        this(bytes, "{name}_{date}{_i}{ending}", new SimpleDateFormat(DEFAULT_DATE_PATTERN));
    }

    public FilesizeCycler(long bytes, String format)
    {
        this(bytes, format, new SimpleDateFormat(DEFAULT_DATE_PATTERN));
    }

    public FilesizeCycler(long bytes, String format, DateFormat dateFormat)
    {
        this.bytes = bytes;
        this.format = format;
        this.dateFormat = dateFormat;
    }

    public File cycle(File file, CloseCallback closeCallBack)
    {
        if (file.length() >= bytes)
        {
            closeCallBack.close(); // Close open stream
            File directory = file.getParentFile();
            String name = file.getName();
            String ending = "";
            if (name.contains("."))
            {
                ending = name.substring(name.lastIndexOf('.'));
                name = name.substring(0, name.lastIndexOf('.'));
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", dateFormat.format(new Date(System.currentTimeMillis())));
            map.put("name", name);
            map.put("ending", ending);
            map.put("_i", "");
            map.put("i", "");
            File cycled = new File(directory, MACRO_PROCESSOR.process(format, map));
            int i = 1;
            while (cycled.exists())
            {
                map.put("_i", "_" + i);
                map.put("i", i++);
                cycled = new File(directory, MACRO_PROCESSOR.process(format, map));
            }
            if (!cycled.getParentFile().exists())
            {
                if (!cycled.getParentFile().mkdirs())
                {
                    throw new LoggingException("Could not create the parent-folder for file to cycle");
                }
            }
            if (!file.renameTo(cycled))
            {
                throw new IllegalStateException("Error when Cycling");
            }
        }
        return file;
    }
}
