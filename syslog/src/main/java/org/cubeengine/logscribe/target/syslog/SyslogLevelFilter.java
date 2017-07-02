package org.cubeengine.logscribe.target.syslog;

import java.util.Map;

import org.cubeengine.logscribe.LogLevel;
import org.cubeengine.logscribe.target.LogLevelFilter;

public class SyslogLevelFilter extends LogLevelFilter
{
    public static final LogLevel EMERGENCY     = new LogLevel("Emergency",     0);
    public static final LogLevel ALERT         = new LogLevel("Alert",         1);
    public static final LogLevel CRITICAL      = new LogLevel("Critical",      2);
    public static final LogLevel ERROR         = new LogLevel("Error",         3);
    public static final LogLevel WARNING       = new LogLevel("Warning",       4);
    public static final LogLevel NOTICE        = new LogLevel("Notice",        5);
    public static final LogLevel INFORMATIONAL = new LogLevel("Informational", 6);
    public static final LogLevel DEBUG         = new LogLevel("Debug",         7);

    @Override
    protected void initLevels(Map<LogLevel, LogLevel> levels)
    {
        levels.put(LogLevel.EMERG,    EMERGENCY);
        levels.put(LogLevel.FATAL,    ALERT);
        levels.put(LogLevel.CRITICAL, CRITICAL);
        levels.put(LogLevel.ERROR,    ERROR);
        levels.put(LogLevel.WARNING,  WARNING);
        levels.put(LogLevel.NOTICE,   NOTICE);
        levels.put(LogLevel.INFO,     INFORMATIONAL);
        levels.put(LogLevel.DEBUG,    DEBUG);
        levels.put(LogLevel.TRACE,    DEBUG);
    }
}
