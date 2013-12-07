package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.FormattedTarget;
import de.cubeisland.engine.logging.LogEntry;

import java.io.PrintStream;

public class PrintTarget extends FormattedTarget<Format>
{
    private final PrintStream stream;

    public PrintTarget(Format format, PrintStream stream)
    {
        super(format);
        this.stream = stream;
    }

    @Override
    protected void publish(LogEntry entry)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
