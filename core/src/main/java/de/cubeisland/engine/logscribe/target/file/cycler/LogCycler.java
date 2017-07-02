package de.cubeisland.engine.logscribe.target.file.cycler;

import java.nio.file.Path;

/**
 * A LogCycler cycle through files under certain conditions
 */
public interface LogCycler
{
    /**
     * Checks if cycling is needed and returns the new File after cycling
     *
     * @param file          the current file
     * @param closeCallBack a callback to close the streams of the current file
     *
     * @return the new file
     */
    Path cycle(Path file, CloseCallback closeCallBack);
}
