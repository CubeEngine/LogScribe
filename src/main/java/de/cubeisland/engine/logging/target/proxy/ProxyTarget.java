package de.cubeisland.engine.logging.target.proxy;

import de.cubeisland.engine.logging.LogLevel;
import de.cubeisland.engine.logging.LogTarget;

public abstract class ProxyTarget<T> extends LogTarget
{
    protected final T handle;

    protected ProxyTarget(T handle)
    {
        this.handle = handle;
    }

    public abstract void setProxyLevel(LogLevel level);
}
