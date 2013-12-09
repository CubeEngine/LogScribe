package de.cubeisland.engine.logging;

import de.cubeisland.engine.logging.target.proxy.LogProxyTarget;

import java.util.Date;
import java.util.LinkedList;

public class Log extends LogBase
{
    private final LogFactory factory;
    private final String id;
    private final Class<?> clazz;
    private final Date birthdate = new Date(System.currentTimeMillis());

    private final LinkedList<LogTarget> targets = new LinkedList<LogTarget>();

    public Log(LogFactory factory, Class<?> clazz, String id)
    {
        this.factory = factory;
        this.id = id;
        this.clazz = clazz;
    }

    Class<?> getClazz()
    {
        return this.clazz;
    }

    public String getId()
    {
        return id;
    }
    public Date getBirthdate()
    {
        return birthdate;
    }

    public Log addTarget(LogTarget target)
    {
        this.targets.add(target);
        return this;
    }

    public LogTarget addDelegate(Log log)
    {
        LogProxyTarget logProxyTarget = new LogProxyTarget(log);
        this.addTarget(logProxyTarget);
        return logProxyTarget;
    }

    public Log removeTarget(LogTarget target)
    {
        this.targets.remove(target);
        return this;
    }

    @Override
    protected void publish(LogEntry entry)
    {
        if (!this.targets.isEmpty())
        {
            for (LogTarget target : this.targets)
            {
                target.log(entry.copy());
            }
        }
    }

    public void shutdown()
    {
        this.factory.shutdown(this);
    }

    void shutdown0()
    {
        for (LogTarget target : this.targets)
        {
            target.shutdown();
        }
        // TODO
    }
}
