package de.cubeisland.engine.logging.filter;

import de.cubeisland.engine.logging.LogEntry;

public interface LogFilter
{
    boolean accept(LogEntry entry);
}
