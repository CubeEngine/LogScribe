package de.cubeisland.engine.logging;

public interface LogFactory
{
    Log getLog(Class<?> clazz, String id);
}
