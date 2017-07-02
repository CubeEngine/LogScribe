package org.cubeengine.logscribe.target.syslog;

import org.cubeengine.logscribe.Flushable;
import org.cubeengine.logscribe.LogEntry;
import org.cubeengine.logscribe.LogTarget;
import org.cubeengine.logscribe.target.format.DefaultFormat;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConfigIF;
import org.graylog2.syslog4j.SyslogIF;

public class SyslogTarget extends LogTarget implements Flushable
{
    private final SyslogIF syslog;

    public SyslogTarget()
    {
        this(Syslog.getInstance("udp"));
        appendFilter(new SyslogLevelFilter());
    }

    public SyslogTarget(String instanceName, SyslogConfigIF config)
    {
        this(Syslog.createInstance(instanceName, config));
    }

    public SyslogTarget(SyslogIF syslog)
    {
        this.syslog = syslog;
    }

    public SyslogIF getSysLogger()
    {
        return this.syslog;
    }

    @Override
    protected void publish(LogEntry entry)
    {
        String message = entry.getMessage();
        if (entry.hasArgs())
        {
            message = DefaultFormat.insertArgs(message, entry.getArgs());
        }
        this.syslog.log(entry.getLevel().getPriority(), message);
    }

    public void flush()
    {
        this.syslog.flush();
    }

    @Override
    protected void onShutdown()
    {
        this.syslog.shutdown();
    }
}
