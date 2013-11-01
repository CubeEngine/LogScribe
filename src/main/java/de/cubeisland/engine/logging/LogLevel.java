package de.cubeisland.engine.logging;

public class LogLevel implements Comparable<LogLevel>
{
    // meta levels
    public static final LogLevel ALL = new LogLevel("ALL", Integer.MIN_VALUE);
    public static final LogLevel NONE = new LogLevel("NONE", Integer.MAX_VALUE);

    // default levels
    public static final LogLevel TRACE = new LogLevel("TRACE", 100);
    public static final LogLevel DEBUG = new LogLevel("DEBUG", 200);
    public static final LogLevel INFO = new LogLevel("INFO", 300);
    public static final LogLevel WARN = new LogLevel("WARN", 400);
    public static final LogLevel ERROR = new LogLevel("ERROR", 500);


    private final String name;
    private final int priority;

    public LogLevel(String name, int priority)
    {
        this.name = name;
        this.priority = priority;
    }

    public String getName()
    {
        return name;
    }

    public int getPriority()
    {
        return priority;
    }

    public int compareTo(LogLevel o)
    {
        if (this.getPriority() < o.getPriority())
        {
            return -1;
        }
        else if (this.getPriority() > o.getPriority())
        {
            return 1;
        }
        return 0;
    }
}
