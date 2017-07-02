package org.cubeengine.logscribe.target.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.cubeengine.logscribe.LogEntry;
import org.cubeengine.logscribe.LogLevel;
import org.cubeengine.logscribe.target.format.DefaultFormat;

/**
 * A ProxyTarget for {@link java.util.logging.Logger
 */
public class JULProxyTarget extends ProxyTarget<Logger>
{
    private final Map<LogLevel, Level> cachedJulLevel = new ConcurrentHashMap<>();

    /**
     * Creates a new ProxyTarget for given Logger
     *
     * @param logger the Logger
     */
    public JULProxyTarget(Logger logger)
    {
        super(logger);
    }

    @Override
    protected void publish(LogEntry entry)
    {
        String parsedMessage = DefaultFormat.insertArgs(entry.getMessage(), entry.getArgs());
        LogRecord logRecord = new LogRecord(this.getJulLevel(entry.getLevel()), parsedMessage);
        logRecord.setMillis(entry.getDateTime().toInstant().toEpochMilli());
        logRecord.setThrown(entry.getThrowable());
        this.handle.log(logRecord);
    }

    @Override
    public void setProxyLevel(LogLevel level)
    {
        this.handle.setLevel(this.getJulLevel(level));
    }

    private Level getJulLevel(LogLevel level)
    {
        return this.cachedJulLevel.computeIfAbsent(level, JulLevel::new);
    }

    private static class JulLevel extends Level
    {
        private JulLevel(LogLevel level)
        {
            super(level.getName(), level.getPriority());
        }
    }
}
