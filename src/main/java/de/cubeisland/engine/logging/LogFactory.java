package de.cubeisland.engine.logging;

public interface LogFactory
{
    Log getLog(Class<?> clazz, String id);
    Log getLog(Class<?> clazz);
    void shutdown();
    void shutdown(Log log);
}
