package de.cubeisland.engine.logscribe.target.syslog;

import de.cubeisland.engine.logscribe.DefaultLogFactory;
import de.cubeisland.engine.logscribe.Log;
import de.cubeisland.engine.logscribe.LogFactory;
import de.cubeisland.engine.logscribe.LogLevel;
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

    public void shutdown()
    {
        this.factory.shutdown();
    }
}
