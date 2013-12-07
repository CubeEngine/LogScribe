package de.cubeisland.engine.logging.target.file.format;

import de.cubeisland.engine.logging.target.Format;

public interface FileFormat extends Format
{
    void writeHeader(StringBuilder builder);
    void writeTrailer(StringBuilder builder);
}
