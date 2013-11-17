package de.cubeisland.engine.logging.target.file.format;

/**
 * Created with IntelliJ IDEA.
 * User: Phillip
 * Date: 17.11.13
 * Time: 23:19
 * To change this template use File | Settings | File Templates.
 */
public interface FileFormat
{
    void writeHeader(StringBuilder builder);
    void writeEntry(StringBuilder builder);
    void writeTrailer(StringBuilder builder);
}
