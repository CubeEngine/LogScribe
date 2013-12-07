package de.cubeisland.engine.logging.target.file.format;

import de.cubeisland.engine.logging.LogEntry;

public interface FileFormat
{
    void writeHeader(StringBuilder builder);
    void writeEntry(StringBuilder builder, LogEntry logEntry);
    void writeTrailer(StringBuilder builder);
}
