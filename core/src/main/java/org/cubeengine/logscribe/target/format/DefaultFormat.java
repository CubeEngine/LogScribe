package org.cubeengine.logscribe.target.format;

import java.time.format.DateTimeFormatter;
import java.util.IdentityHashMap;
import java.util.Map;

import org.cubeengine.logscribe.LogEntry;

import static org.cubeengine.logscribe.MacroProcessor.processMacros;
import static org.cubeengine.logscribe.MacroProcessor.processSimpleMacros;

/**
 * A Simple Plain Text Format
 *
 * <p>This format allows <code>{msg}, {date} and {level}</code> as macros</p>
 */
public class DefaultFormat implements Format
{
    private static final String MACRO_MESSAGE = "msg";
    private static final String MACRO_DATE = "date";
    private static final String MACRO_LEVEL = "level";

    public static final String DEFAULT_FORMAT = "{" + MACRO_DATE + "} [{" + MACRO_LEVEL + "}] {" + MACRO_MESSAGE + "}";

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
     * <p><code>{date} [{level}] {msg}</code></p>
     * <p>and the default SimpleDateFormat</p>
     */
    public DefaultFormat()
    {
        this(DEFAULT_FORMAT);
    }

    public void writeEntry(LogEntry logEntry, StringBuilder builder)
    {
        Map<String, Object> map = new IdentityHashMap<>(3);
        map.put(MACRO_MESSAGE, processSimpleMacros(logEntry.getMessage(), logEntry.getArgs()));
        map.put(MACRO_DATE, dateFormat.format(logEntry.getDateTime()));
        map.put(MACRO_LEVEL, logEntry.getLevel().getName());
        builder.append(processMacros(format, map)).append("\n");
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
}
