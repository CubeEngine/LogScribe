package org.cubeengine.logscribe.target.file.format;

import java.time.format.DateTimeFormatter;
import org.cubeengine.logscribe.LogEntry;

import static org.cubeengine.logscribe.MacroProcessor.processSimpleMacros;

public class CSVFileFormat implements FileFormat
{
    public static final String COMMA = ",";
    private static final char QUOTE = '"';

    private final String separator;

    public CSVFileFormat()
    {
        this(COMMA);
    }

    public CSVFileFormat(String separator)
    {
        this.separator = separator;
    }

    public void writeHeader(StringBuilder builder)
    {
    }

    public void writeEntry(LogEntry logEntry, StringBuilder builder)
    {
        appendQuoted(builder, logEntry.getDateTime().format(DateTimeFormatter.ISO_INSTANT));
        builder.append(separator);
        appendQuoted(builder, logEntry.getLevel().getName());
        builder.append(separator);
        appendQuoted(builder, processSimpleMacros(logEntry.getMessage(), logEntry.getArgs()));
        builder.append(separator);
        appendThrowable(builder, logEntry.getThrowable());
    }

    public void writeTrailer(StringBuilder builder)
    {
    }

    private static void appendThrowable(StringBuilder out, Throwable t)
    {
        if (t == null)
        {
            out.append("null");
        }
        else
        {
            // TODO more complete serialization of throwable
            out.append(QUOTE);
            appendEscapingQuote(out, t.getClass().getName());
            out.append('(');
            appendEscapingQuote(out, t.getMessage());
            out.append(')');
            out.append(QUOTE);
        }
    }

    private static void appendQuoted(StringBuilder out, String input)
    {
        out.append(QUOTE);
        appendEscapingQuote(out, input);
        out.append(QUOTE);
    }

    private static void appendEscapingQuote(StringBuilder out, String input)
    {
        if (input != null)
        {
            char current;
            for (int i = 0; i < input.length(); ++i)
            {
                current = input.charAt(i);
                if (current == QUOTE)
                {
                    out.append(QUOTE);
                }
                out.append(current);
            }
        }
    }
}
