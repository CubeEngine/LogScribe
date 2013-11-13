package de.cubeisland.engine.logging;

import java.util.Date;

public class FinalizedLogEntry
{
    private final LogLevel level;
    private final Throwable throwable;
    private final String message;
    private final Object[] args;
    private final Date date;

    public FinalizedLogEntry(LogLevel level, Throwable throwable, String message, Object[] args, Date date)
    {
        this.level = level;
        this.throwable = throwable;
        this.message = message;
        this.args = args;
        this.date = date;
    }

    public LogLevel getLevel()
    {
        return level;
    }

    public Throwable getThrowable()
    {
        return throwable;
    }

    public String getMessage()
    {
        return message;
    }

    public Object[] getArgs()
    {
        return args.clone();
    }
}
