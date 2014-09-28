package de.cubeisland.engine.logscribe.target.proxy;

import de.cubeisland.engine.logscribe.Log;
import de.cubeisland.engine.logscribe.LogEntry;
import de.cubeisland.engine.logscribe.LogLevel;

/**
 * A ProxyTarget for {@link de.cubeisland.engine.logscribe.Log}
 */
public class LogProxyTarget extends ProxyTarget<Log>
{
    /**
     * Creates a new ProxyTarget for given Log
     *
     * @param handle the Log
     */
    public LogProxyTarget(Log handle)
    {
        super(handle);
    }

    @Override
    protected void publish(LogEntry entry)
    {
        this.handle.log(entry);
    }

    @Override
    public void setProxyLevel(LogLevel level)
    {
        this.handle.setLevel(level);
    }
}
