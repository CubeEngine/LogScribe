package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.DefaultFormat;
import de.cubeisland.engine.logging.FormattedTarget;
import de.cubeisland.engine.logging.LogEntry;

import java.io.PrintStream;

public class PrintTarget extends FormattedTarget<Format>
{
    public static PrintTarget STDEER = new PrintTarget(System.err, new DefaultFormat());
    public static PrintTarget STDOUT = new PrintTarget(System.out, new DefaultFormat());

    private final PrintStream stream;

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
}
