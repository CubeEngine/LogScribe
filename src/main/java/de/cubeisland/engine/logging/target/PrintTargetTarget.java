package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogTarget;

import java.io.PrintStream;

public class PrintTargetTarget extends LogTarget
{
    private final PrintStream stream;

    public PrintTargetTarget(PrintStream stream)
    {
        this.stream = stream;
    }

    @Override
    public void publish(LogEntry entry)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
