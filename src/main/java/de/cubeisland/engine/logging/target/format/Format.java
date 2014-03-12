package de.cubeisland.engine.logging.target.format;

import de.cubeisland.engine.logging.LogEntry;

/**
 * A Formatter for a LogEntry
 */
public interface Format
{
    /**
     * Writes the contents of the logEntry into the StringBuilder
     *
     * @param logEntry the logEntry
     * @param builder the builder
     */
    void writeEntry(LogEntry logEntry, StringBuilder builder);
}
