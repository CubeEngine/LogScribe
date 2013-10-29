package de.cubeisland.engine.logging;

public interface LogFilter
{
    boolean accept(LogEntry entry);
}
