package de.cubeisland.engine.logging.filter;

import de.cubeisland.engine.logging.LogEntry;

public class PrefixFilter implements LogFilter
{
    private final String prefix;

    public PrefixFilter(String prefix)
    {
        this.prefix = prefix;
    }

    public boolean accept(LogEntry entry)
    {
        entry.setMessage(prefix + entry.getMessage());
        return true;
    }
}
