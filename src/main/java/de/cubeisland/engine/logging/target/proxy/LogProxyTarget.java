package de.cubeisland.engine.logging.target.proxy;

import de.cubeisland.engine.logging.Log;
import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogLevel;

/**
 * A ProxyTarget for {@link de.cubeisland.engine.logging.Log}
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

    @Override
    protected void onShutdown()
    {
        this.handle.shutdown();
    }
}
