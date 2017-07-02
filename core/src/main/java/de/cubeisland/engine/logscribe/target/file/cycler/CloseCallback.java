package de.cubeisland.engine.logscribe.target.file.cycler;

@FunctionalInterface
public interface CloseCallback
{
    /**
     * Closes the underlying stream.
     * <p>If the stream is already closed this will do nothing
     */
    void close();
}
