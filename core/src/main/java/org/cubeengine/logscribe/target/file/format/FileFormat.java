package org.cubeengine.logscribe.target.file.format;

import org.cubeengine.logscribe.target.format.Format;

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
