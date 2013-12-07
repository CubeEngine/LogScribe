package de.cubeisland.engine.logging;

import java.util.LinkedList;

public abstract class LogTarget
{
    protected LinkedList<LogFilter> filters = new LinkedList<LogFilter>();

    public final void log(LogEntry entry)
    {
        for (LogFilter filter : this.filters)
        {
            if (!filter.accept(entry))
            {
                return;
            }
        }

        this.publish(entry);
    }

    protected abstract void publish(LogEntry entry);

    public final LogTarget prependFilter(LogFilter filter)
    {
        this.filters.addFirst(filter);
        return this;
    }

    public final LogTarget appendFilter(LogFilter filter)
    {
        this.filters.addLast(filter);
        return this;
    }

    public final LogTarget removeFilter(LogFilter filter)
    {
        this.filters.remove(filter);
        return this;
    }
}
