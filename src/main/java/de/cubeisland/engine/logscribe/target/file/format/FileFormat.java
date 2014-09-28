package de.cubeisland.engine.logscribe.target.file.format;

import de.cubeisland.engine.logscribe.target.format.Format;

/**
 * A FileFormatter providing methods to add header and trailer to the file
 */
public interface FileFormat extends Format
{
    /**
     * Appends a Header to the given StringBuilder
     *
     * @param builder the StringBuilder to append to
     */
    void writeHeader(StringBuilder builder);
    /**
     * Appends a Trailer to the given StringBuilder
     *
     * @param builder the StringBuilder to append to
     */
    void writeTrailer(StringBuilder builder);
}
