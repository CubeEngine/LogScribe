package de.cubeisland.engine.logscribe.target.proxy;

import de.cubeisland.engine.logscribe.LogLevel;
import de.cubeisland.engine.logscribe.LogTarget;

/**
 * An abstract proxy target
 * <p>This Target delegates a LogEntry to an other Logger
 *
 * @param <T>
 */
public abstract class ProxyTarget<T> extends LogTarget
{
    protected final T handle;

    /**
     * Creates a new ProxyTarget for given handle
     *
     * @param handle the proxied Logger
     */
    protected ProxyTarget(T handle)
    {
        this.handle = handle;
    }

    /**
     * Sets the level of the proxied Logger
     *
     * @param level the LogLevel to set
     */
    public abstract void setProxyLevel(LogLevel level);

    @Override
    protected void onShutdown()
    {
        // Do nothing
    }
}
