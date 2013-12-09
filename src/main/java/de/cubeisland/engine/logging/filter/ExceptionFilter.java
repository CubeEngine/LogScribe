package de.cubeisland.engine.logging.filter;

import de.cubeisland.engine.logging.LogEntry;

public class ExceptionFilter implements LogFilter
{
    public boolean accept(LogEntry entry)
    {
        return entry.getThrowable() != null;
    }
}
