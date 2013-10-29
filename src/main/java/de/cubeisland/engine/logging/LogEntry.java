package de.cubeisland.engine.logging;

public class LogEntry
{
    private LogLevel level;
    private Throwable throwable;
    private String message;
    private Object[] args;

    public LogEntry(LogLevel level, Throwable throwable, String message, Object[] args)
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

    public void setLevel(LogLevel level)
    {
        this.level = level;
    }

    public Throwable getThrowable()
    {
        return throwable;
    }

    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Object[] getArgs()
    {
        return args;
    }
}
