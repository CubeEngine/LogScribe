package de.cubeisland.engine.logging;

public abstract class LogTarget extends Filterable
{
    private boolean isShutdown = false;

    public void shutdown()
    {
        if (!isShutdown)
        {
            this.shutdown0();
            this.isShutdown = true;
        }
    }

    protected abstract void shutdown0();
}
