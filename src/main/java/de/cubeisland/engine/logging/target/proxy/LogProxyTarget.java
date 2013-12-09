package de.cubeisland.engine.logging.target.proxy;

import de.cubeisland.engine.logging.Log;
import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogLevel;

public class LogProxyTarget extends ProxyTarget<Log>
{
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
    protected void shutdown()
    {
        this.handle.shutdown();
    }
}
