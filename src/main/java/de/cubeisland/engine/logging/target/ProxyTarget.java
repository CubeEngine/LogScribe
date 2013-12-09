package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.LogTarget;

public abstract class ProxyTarget<T> extends LogTarget
{
    protected final T handle;

    protected ProxyTarget(T handle)
    {
        this.handle = handle;
    }
}
