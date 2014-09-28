package de.cubeisland.engine.logscribe;

/**
 * This interface adds flushing capabilities to class.
 */
public interface Flushable
{
    /**
     * This method writes out any cached data
     */
    void flush();
}
