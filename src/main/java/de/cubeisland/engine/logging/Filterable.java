package de.cubeisland.engine.logging;

import de.cubeisland.engine.logging.filter.LogFilter;

import java.util.LinkedList;

public abstract class Filterable
{
    protected LinkedList<LogFilter> filters = new LinkedList<LogFilter>();
    protected LogLevel level = LogLevel.ALL;

    public final void prependFilter(LogFilter filter)
    {
        this.filters.addFirst(filter);
    }

    public final void appendFilter(LogFilter filter)
    {
        this.filters.addLast(filter);
    }

    public final void removeFilter(LogFilter filter)
    {
        this.filters.remove(filter);
    }


    public final void removeFirstFilter()
    {
        this.filters.removeFirst();
    }

    public final void removeLastFilter()
    {
        this.filters.removeFirst();
    }

    public void setLevel(LogLevel level)
    {
        this.level = level;
    }

    public LogLevel getLevel()
    {
        return level;
    }

    public void log(final LogEntry entry)
    {
        if (level.compareTo(this.level) < 0)
        {
            return;
        }
        if (!this.filters.isEmpty())
        {
            for (LogFilter filter : this.filters)
            {
                if (!filter.accept(entry))
                {
                    return;
                }
            }
        }
        this.publish(entry);
    }

    protected abstract void publish(LogEntry entry);
}
