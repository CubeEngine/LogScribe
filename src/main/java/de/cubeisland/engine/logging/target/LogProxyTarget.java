package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.Log;
import de.cubeisland.engine.logging.LogEntry;

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
}
