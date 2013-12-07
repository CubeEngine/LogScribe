package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JULProxyTarget extends ProxyTarget
{
    private final Map<LogLevel, Level> cachedJulLevel = new HashMap<LogLevel, Level>();
    private final Logger logger;

    public JULProxyTarget(Logger logger)
    {
        this.logger = logger;
    }

    @Override
    protected void publish(LogEntry entry)
    {
        // TODO change placeholder for args in msg
        LogRecord logRecord = new LogRecord(this.getJulLevel(entry.getLevel()), entry.getMessage());
        logRecord.setMillis(entry.getDate().getTime());
        logRecord.setParameters(entry.getArgs());
        logRecord.setThrown(entry.getThrowable());
        this.logger.log(logRecord);
    }

    private Level getJulLevel(LogLevel level)
    {
        Level julLevel = this.cachedJulLevel.get(level);
        if (julLevel == null)
        {
            julLevel = new JulLevel(level);
            this.cachedJulLevel.put(level, julLevel);
        }
        return julLevel;
    }

    private class JulLevel extends Level
    {
        private JulLevel(LogLevel level)
        {
            super(level.getName(), level.getPriority());
        }
    }
}
