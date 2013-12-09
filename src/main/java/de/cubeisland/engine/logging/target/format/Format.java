package de.cubeisland.engine.logging.target.format;

import de.cubeisland.engine.logging.LogEntry;

public interface Format
{
    void writeEntry(LogEntry logEntry, StringBuilder builder);
}
