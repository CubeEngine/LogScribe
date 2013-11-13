package de.cubeisland.engine.logging;

import java.util.LinkedList;

public abstract class LogTarget
{
    private LinkedList<LogDecorator> decorators = new LinkedList<LogDecorator>();

    public abstract void log(FinalizedLogEntry entry);

    public LogTarget prependDecorator(LogDecorator decorator)
    {
        this.decorators.addFirst(decorator);
        return this;
    }

    public LogTarget appendDecorator(LogDecorator decorator)
    {
        this.decorators.addLast(decorator);
        return this;
    }

    public LogTarget removeDecorator(LogDecorator decorator)
    {
        this.decorators.remove(decorator);
        return this;
    }
}
