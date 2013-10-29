package de.cubeisland.engine.logging;

public class FinalizedLogEntry
{
    private final LogLevel level;
    private final Throwable throwable;
    private final String message;
    private Object[] args;

    public FinalizedLogEntry(LogLevel level, Throwable throwable, String message, Object[] args)
    {
        this.level = level;
        this.throwable = throwable;
        this.message = message;
        this.args = args;
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
