package org.cubeengine.logscribe.target.syslog;

import org.cubeengine.logscribe.DefaultLogFactory;
import org.cubeengine.logscribe.Log;
import org.cubeengine.logscribe.LogFactory;
import org.cubeengine.logscribe.LogLevel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SyslogTargetTest
{
    private final LogFactory factory = new DefaultLogFactory();
    private Log log;

    @Before
    public void startup()
    {
        this.log = factory.getLog(getClass());
        this.log.addTarget(new SyslogTarget());
    }

    @Test
    public void testPublish() throws Exception
    {
        log.log(LogLevel.DEBUG, "Test 123");
        log.flush();
    }

    @After
    public void shutdown()
    {
        this.factory.shutdown();
    }
}
