package de.cubeisland.engine.logging;

import java.util.Iterator;
import java.util.LinkedList;

public class Log
{
    private static final Object[] NO_ARGS = {};
    private Log parent;
    private final Lazy<LinkedList<LogFilter>> filters = new Lazy<LinkedList<LogFilter>>() {
        @Override
        public LinkedList<LogFilter> initialize()
        {
            return new LinkedList<LogFilter>();
        }
    };
    private final Lazy<LinkedList<LogTarget>> targets = new Lazy<LinkedList<LogTarget>>() {
        @Override
        public LinkedList<LogTarget> initialize()
        {
            return new LinkedList<LogTarget>();
        }
    };
    private LogLevel level;

    public LogLevel getLevel()
    {
        return level;
    }

    public Log setLevel(LogLevel level)
    {
        this.level = level;
        return this;
    }

    public Log prependFilter(LogFilter filter)
    {
        this.filters.get().addFirst(filter);
        return this;
    }

    public Log appendFilter(LogFilter filter)
    {
        this.filters.get().addLast(filter);
        return this;
    }

    public Log removeFilter(LogFilter filter)
    {
        this.filters.get().remove(filter);
        return this;
    }

    public Log removeFirstFilter()
    {
        this.filters.get().removeFirst();
        return this;
    }

    public Log removeLastFilter()
    {
        this.filters.get().removeFirst();
        return this;
    }

    public Log addTarget(LogTarget target)
    {
        this.targets.get().add(target);
        return this;
    }

    public Log removeTarget(LogTarget target)
    {
        this.targets.get().remove(target);
        return this;
    }

    public void log(LogLevel level, String message)
    {
        this.log(level, message, NO_ARGS);
    }

    public void log(LogLevel level, Throwable t, String message)
    {
        this.log(level, t, message, NO_ARGS);
    }

    public void log(LogLevel level, String message, Object... args)
    {
        this.log(level, null, message, args);
    }

    public void log(LogLevel level, Throwable t, String message, Object... args)
    {
        if (level.compareTo(this.level) < 0)
        {
            return;
        }

        LogEntry entry = new LogEntry(level, t, message, args);

        if (this.filters.has())
        {
            for (LogFilter filter : this.filters.get())
            {
                if (!filter.accept(entry))
                {
                    return;
                }
            }
        }

        FinalizedLogEntry finalizedEntry = new FinalizedLogEntry(entry.getLevel(), entry.getThrowable(), entry.getMessage(), entry.getArgs());

        if (this.targets.has())
        {
            for (LogTarget target : this.targets.get())
            {
                target.log(finalizedEntry);
            }
        }
    }

    public void trace(String message)
    {
        this.trace(message, NO_ARGS);
    }

    public void trace(Throwable t, String message)
    {
        this.trace(t, message, NO_ARGS);
    }

    public void trace(String message, Object... args)
    {
        this.log(LogLevel.TRACE, message, args);
    }

    public void trace(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.TRACE, t, message, args);
    }

    public void debug(String message)
    {
        this.debug(message, NO_ARGS);
    }

    public void debug(Throwable t, String message)
    {
        this.debug(t, message, NO_ARGS);
    }

    public void debug(String message, Object... args)
    {
        this.log(LogLevel.DEBUG, message, args);
    }

    public void debug(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.DEBUG, t, message, args);
    }

    public void info(String message)
    {
        this.info(message, NO_ARGS);
    }

    public void info(Throwable t, String message)
    {
        this.info(t, message, NO_ARGS);
    }

    public void info(String message, Object... args)
    {
        this.log(LogLevel.INFO, message, args);
    }

    public void info(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.INFO, t, message, args);
    }

    public void warn(String message)
    {
        this.warn(message, NO_ARGS);
    }

    public void warn(Throwable t, String message)
    {
        this.warn(t, message, NO_ARGS);
    }

    public void warn(String message, Object... args)
    {
        this.log(LogLevel.WARN, message, args);
    }

    public void warn(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.WARN, t, message, args);
    }

    public void error(String message)
    {
        this.error(message, NO_ARGS);
    }

    public void error(Throwable t, String message)
    {
        this.error(t, message, NO_ARGS);
    }

    public void error(String message, Object... args)
    {
        this.log(LogLevel.ERROR, message, args);
    }

    public void error(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.ERROR, t, message, args);
    }
}
