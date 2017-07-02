package org.cubeengine.logscribe.target.proxy;

import org.cubeengine.logscribe.Log;
import org.cubeengine.logscribe.LogEntry;
import org.cubeengine.logscribe.LogLevel;

/**
 * A ProxyTarget for {@link org.cubeengine.logscribe.Log}
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
