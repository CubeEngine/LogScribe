package de.cubeisland.engine.logging;

public class DefaultLogFactory implements LogFactory
{
    public Log getLog(String id)
    {
        return new Log();
    }
}
