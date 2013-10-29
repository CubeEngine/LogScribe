package de.cubeisland.engine.logging;

public abstract class Lazy<T>
{
    private T instance = null;

    public synchronized T get()
    {
        if (this.instance == null)
        {
            this.instance = this.initialize();
        }
        return this.instance;
    }

    public synchronized boolean has()
    {
        return this.instance != null;
    }

    public synchronized T clear()
    {
        final T instance = this.instance;
        this.instance = null;
        return instance;
    }

    public abstract T initialize();
}
