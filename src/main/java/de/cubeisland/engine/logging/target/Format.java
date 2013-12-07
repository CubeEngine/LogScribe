package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.LogEntry;

public interface Format
{
    void writeEntry(LogEntry logEntry, StringBuilder builder);
}
