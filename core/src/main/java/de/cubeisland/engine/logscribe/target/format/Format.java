package de.cubeisland.engine.logscribe.target.format;

import de.cubeisland.engine.logscribe.LogEntry;

/**
 * A Formatter for a LogEntry
 */
public interface Format
{
    /**
     * Writes the contents of the logEntry into the StringBuilder
     *
     * @param logEntry the logEntry
     * @param builder  the builder
     */
    void writeEntry(LogEntry logEntry, StringBuilder builder);
}
