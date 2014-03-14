package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.target.format.DefaultFormat;
import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.target.format.Format;

import java.io.PrintStream;

/**
 * A LogTarget publishing to a PrintStream
 */
public class PrintTarget extends FormattedTarget<Format>
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
        this.format.writeEntry(entry, sb);
        stream.println(sb.toString());
    }

    @Override
    protected void shutdown0()
    {
        this.stream.close(); // TODO test if this is working correctly
    }
}
