package de.cubeisland.engine.logging.target.proxy;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogLevel;
import de.cubeisland.engine.logging.target.format.DefaultFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A ProxyTarget for {@link java.util.logging.Logger
 */
public class JULProxyTarget extends ProxyTarget<Logger>
{
    private final Map<LogLevel, Level> cachedJulLevel = new HashMap<LogLevel, Level>();

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
        String parsedMessage = DefaultFormat.parseArgs(entry.getMessage(), entry.getArgs());
        LogRecord logRecord = new LogRecord(this.getJulLevel(entry.getLevel()), parsedMessage);
        logRecord.setMillis(entry.getDate().getTime());
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
        Level julLevel = this.cachedJulLevel.get(level);
        if (julLevel == null)
        {
            julLevel = new JulLevel(level);
            this.cachedJulLevel.put(level, julLevel);
        }
        return julLevel;
    }

    private static class JulLevel extends Level
    {
        private JulLevel(LogLevel level)
        {
            super(level.getName(), level.getPriority());
        }
    }

    @Override
    protected void onShutdown()
    {
        // JUL uses shutdown hooks so instead remove all handlers
        for (Handler handler : this.handle.getHandlers())
        {
            this.handle.removeHandler(handler);
        }
        // And Set LogLevel to OFF
        this.handle.setLevel(Level.OFF);
    }
}
