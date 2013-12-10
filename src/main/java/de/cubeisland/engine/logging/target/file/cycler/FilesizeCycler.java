package de.cubeisland.engine.logging.target.file.cycler;

import de.cubeisland.engine.logging.MacroProcessor;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FilesizeCycler implements LogCycler
{
    private static final MacroProcessor macroProcessor = new MacroProcessor();

    // {i} {date} {name} {ending}

    private long bytes;
    private String format;
    private DateFormat dateFormat;

    public FilesizeCycler(long bytes)
    {
        this(bytes, "{name}_{date}{_i}{ending}", new SimpleDateFormat("yyyy-MM-dd--HHmm"));
    }

    public FilesizeCycler(long bytes, String format)
    {
        this(bytes, format, new SimpleDateFormat("yyyy-MM-dd--HHmm"));
    }

    public FilesizeCycler(long bytes, String format, DateFormat dateFormat)
    {
        this.bytes = bytes;
        this.format = format;
        this.dateFormat = dateFormat;
    }

    public File cycle(File file, Runnable closeCallBack)
    {
        if (file.length() >= bytes)
        {
            closeCallBack.run(); // Close open stream
            File directory = file.getParentFile();
            String name = file.getName();
            String ending = "";
            if (name.contains("."))
            {
                ending = name.substring(name.lastIndexOf("."));
                name = name.substring(0, name.lastIndexOf("."));
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", dateFormat.format(new Date(System.currentTimeMillis())));
            map.put("name", name);
            map.put("ending", ending);
            map.put("_i", "");
            map.put("i", "");
            File cycled = new File(directory, macroProcessor.process(format, map));
            int i = 1;
            while (cycled.exists())
            {
                map.put("_i", "_" + i);
                map.put("i", i++);
                cycled = new File(directory, macroProcessor.process(format, map));
            }
            if (!cycled.getParentFile().exists())
            {
                cycled.getParentFile().mkdirs();
            }
            if (!file.renameTo(cycled))
            {
                throw new IllegalStateException("Error when Cycling");
            }
        }
        return file;
    }
}
