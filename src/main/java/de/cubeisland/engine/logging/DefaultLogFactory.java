package de.cubeisland.engine.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultLogFactory implements LogFactory
{
    private final Map<Class<?>, Map<String, Log>> logs;

    public DefaultLogFactory()
    {
        this.logs = new ConcurrentHashMap<Class<?>, Map<String, Log>>();
    }

    public Log getLog(Class<?> clazz, String id)
    {
        Map<String, Log> logMap = this.logs.get(clazz);
        if (logMap == null)
        {
            this.logs.put(clazz, logMap = new ConcurrentHashMap<String, Log>());
        }
        Log log = logMap.get(id);
        if (log == null)
        {
            logMap.put(id, log = new Log(id));
        }
        return log;
    }
}
