package de.cubeisland.engine.logging.target.format;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.MacroProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A Simple Plain Text Format
 * <p/>
 * This format allows {msg}, {date} and {level} as macros
 */
public class DefaultFormat implements Format
{
    private static final MacroProcessor MACRO_PROCESSOR = new MacroProcessor();
    private static final String ARG = "\\{\\}";

    protected final DateFormat dateFormat;
    private final String format;

    /**
     * Creates a new Format for given FormatString and DateFormat
     *
     * @param format     the Format String
     * @param dateFormat the DateFormat
     */
    public DefaultFormat(String format, DateFormat dateFormat)
    {
        assert format != null : "The format String cannot be null";
        assert dateFormat != null : "The DateFormatter cannot be nul";
        this.format = format;
        this.dateFormat = dateFormat;
    }

    /**
     * Creates a new Format for given FormatString with the default SimpleDateFormat
     *
     * @param format the Format String
     */
    public DefaultFormat(String format)
    {
        this(format, new SimpleDateFormat());
    }

    /**
     * Creates a new Format using this Format String:
     * <p>{date} [{level}] {msg}
     * <p>and the default SimpleDateFormat
     */
    public DefaultFormat()
    {
        this("{date} [{level}] {msg}");
    }

    public void writeEntry(LogEntry logEntry, StringBuilder builder)
    {
        String message = String.valueOf(logEntry.getMessage());
        Map<String, Object> map = new HashMap<String, Object>();
        if (logEntry.getArgs() != null && logEntry.getArgs().length != 0)
        {
            message = DefaultFormat.parseArgs(message, logEntry.getArgs());
        }
        map.put("msg", message);
        map.put("date", dateFormat.format(logEntry.getDate()));
        map.put("level", logEntry.getLevel().getName());
        builder.append(MACRO_PROCESSOR.process(format, map));
        writeThrowable(logEntry, builder);
    }

    private void writeThrowable(LogEntry logEntry, StringBuilder builder)
    {
        if (logEntry.hasThrowable())
        {
            builder.append("\n");
            Throwable throwable = logEntry.getThrowable();
            if (throwable.getLocalizedMessage() != null && !throwable.getLocalizedMessage().equals(logEntry.getMessage()))
            {
                builder.append(logEntry.getMessage()).append("\n");
            }
            do
            {
                builder.append(throwable.getClass().getName());
                if (throwable.getLocalizedMessage() != null)
                {
                    builder.append(": ").append(throwable.getLocalizedMessage());
                }
                builder.append("\n");
                for (StackTraceElement element : throwable.getStackTrace())
                {
                    builder.append("\tat ").append(element).append("\n");
                }
                throwable = throwable.getCause();
                if (throwable != null)
                {
                    builder.append("Caused by: ");
                }
            }
            while (throwable != null);
        }
    }

    /**
     * Parses the messageArguments into the message
     *
     * @param msg  the message
     * @param args the arguments
     *
     * @return the resulting message
     */
    public static String parseArgs(String msg, Object... args)
    {
        if (args == null || args.length == 0)
        {
            return msg;
        }
        int i = 0;
        String result = msg;
        while (result.contains("{}"))
        {
            try
            {
                result = result.replaceFirst(ARG, "\\{" + i++ + "\\}");
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                throw new IllegalArgumentException("Not enough arguments!", e);
            }
        }
        for (i = 0; i < args.length; i++)
        {
            if (result.contains("{"))
            {
                result = result.replaceAll("\\{" + i + "\\}", String.valueOf(args[i]).replace("\\", "\\\\"));
            }
            else
            {
                break;
            }
        }
        return result;
    }
}
