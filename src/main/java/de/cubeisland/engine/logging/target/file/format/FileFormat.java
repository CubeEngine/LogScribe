package de.cubeisland.engine.logging.target.file.format;

import de.cubeisland.engine.logging.target.format.Format;

/**
 * A FileFormatter providing methods to add header and trailer to the file
 */
public interface FileFormat extends Format
{
    void writeHeader(StringBuilder builder);
    void writeTrailer(StringBuilder builder);
}
