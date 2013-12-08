package de.cubeisland.engine.logging.target.file.format;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.MacroProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class LogFileFormat implements FileFormat
{
    private final DateFormat dateFormat;
    private final String format;

    public LogFileFormat(String format)
    {
        this(format, new SimpleDateFormat());
    }

    public LogFileFormat(String format, DateFormat dateFormat)
    {
        assert format != null : "The format String cannot be null";
        assert dateFormat != null : "The DateFormatter cannot be nul";
        this.format = format;
        this.dateFormat = dateFormat;
    }

    public void writeHeader(StringBuilder builder)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private static final String arg = "\\{\\}";
    private static final MacroProcessor macroProcessor = new MacroProcessor();

    public void writeEntry(LogEntry logEntry, StringBuilder builder)
    {
        String message = logEntry.getMessage();
        Map<String, Object> map = new HashMap<String, Object>();
        if (logEntry.getArgs() != null && logEntry.getArgs().length != 0)
        {
            int i = 0;
            while (message.contains(arg))
            {
                try
                {
                    message = message.replaceFirst(arg, String.valueOf(logEntry.getArgs()[i++]));
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    throw new IllegalArgumentException("Not enough arguments!", e);
                }
            }
            Object[] args = logEntry.getArgs();
            for (i = 0; i < args.length; i++)
            {
                map.put("{" + i + "}", args[i]);
            }
        }
        map.put("{msg}", message);
        map.put("{date}", dateFormat.format(logEntry.getDate()));
        map.put("{level}", logEntry.getLevel().getName());
        if (logEntry.getThrowable() != null)
        {
            map.put("{exmsg}", logEntry.getThrowable().getLocalizedMessage());
        }
        message = macroProcessor.process(format, map);
        // TODO what to do with exception stacktrace
        builder.append(message);

        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeTrailer(StringBuilder builder)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
