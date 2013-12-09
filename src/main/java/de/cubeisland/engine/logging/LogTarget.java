package de.cubeisland.engine.logging;

public abstract class LogTarget extends Filterable
{
    protected abstract void shutdown();
}
