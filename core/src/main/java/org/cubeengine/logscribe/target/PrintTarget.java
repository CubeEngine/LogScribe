package org.cubeengine.logscribe.target;

import java.io.PrintStream;

import org.cubeengine.logscribe.Flushable;
import org.cubeengine.logscribe.LogEntry;
import org.cubeengine.logscribe.target.format.DefaultFormat;
import org.cubeengine.logscribe.target.format.Format;

/**
 * A LogTarget publishing to a PrintStream
 */
public class PrintTarget extends FormattedTarget<Format> implements Flushable
{
    public static final PrintTarget STDERR = new PrintTarget(System.err, new DefaultFormat());
    public static final PrintTarget STDOUT = new PrintTarget(System.out, new DefaultFormat());

    private final PrintStream stream;

    /**
     * Creates a new PrintTarget with given PrintStream and Format
     *
     * @param stream the PrintStream
     * @param format the Format
     */
    public PrintTarget(PrintStream stream, Format format)
    {
        super(format);
        this.stream = stream;
    }

    @Override
    protected void publish(LogEntry entry)
    {
        StringBuilder sb = new StringBuilder();
        getFormat().writeEntry(entry, sb);
        // print() instead of println() as the linebreak is provided by the format
        stream.print(sb.toString());
    }

    public void flush()
    {
        this.stream.flush();
    }

    @Override
    protected void onShutdown()
    {
        this.stream.close();
    }
}
