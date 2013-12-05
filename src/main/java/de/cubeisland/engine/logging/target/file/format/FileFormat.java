package de.cubeisland.engine.logging.target.file.format;

public interface FileFormat
{
    void writeHeader(StringBuilder builder);
    void writeEntry(StringBuilder builder);
    void writeTrailer(StringBuilder builder);
}
