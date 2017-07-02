package org.cubeengine.logscribe.target.format;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.cubeengine.logscribe.LogEntry;
import org.cubeengine.logscribe.MacroProcessor;

/**
 * A Simple Plain Text Format
 * <p/>
 * This format allows {msg}, {date} and {level} as macros
 */
public class DefaultFormat implements Format
{
    private static final MacroProcessor MACRO_PROCESSOR = new MacroProcessor();
    private static final String ARG = "\\{\\}";

    protected final DateTimeFormatter dateFormat;
    private final String format;

    /**
     * Creates a new Format for given FormatString and DateFormat
     *
     * @param format     the Format String
     * @param dateFormat the DateFormat
     */
    public DefaultFormat(String format, DateTimeFormatter dateFormat)
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
        this(format, DateTimeFormatter.ISO_DATE_TIME);
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
        if (logEntry.hasArgs())
        {
            message = DefaultFormat.insertArgs(message, logEntry.getArgs());
        }
        map.put("msg", message);
        map.put("date", dateFormat.format(logEntry.getDateTime()));
        map.put("level", logEntry.getLevel().getName());
        builder.append(MACRO_PROCESSOR.process(format, map)).append("\n");
        writeThrowable(logEntry, builder);
    }

    private void writeThrowable(LogEntry logEntry, StringBuilder builder)
    {
        if (logEntry.hasThrowable())
        {
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
    public static String insertArgs(String msg, Object... args)
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
                result = result.replace("{" + i + "}", String.valueOf(args[i]));
            }
            else
            {
                break;
            }
        }
        return result;
    }
}
