package org.cubeengine.logscribe.target;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.cubeengine.logscribe.DefaultLogFactory;
import org.cubeengine.logscribe.Log;
import org.cubeengine.logscribe.target.format.DefaultFormat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PrintTargetTest
{
    private DefaultLogFactory factory;

    @Before
    public void setUp() throws Exception
    {
        this.factory = new DefaultLogFactory();
    }

    @Test
    public void testPrintTarget()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintTarget target = new PrintTarget(new PrintStream(baos), new DefaultFormat("{msg}"));
        Log log = factory.getLog(PrintTargetTest.class);
        log.addTarget(target);
        log.info("PrintTargetTest");
        // Trim to remove \r\n
        assertEquals("PrintTargetTest", baos.toString().trim());
        // Reset OutputStream
        baos.reset();
        log.shutdown();
        log.info("PrintTarget Shutdown Test");
        // LogTarget is Shutdown nothing should arrive in the PrintStream/OutputStream
        assertTrue(!"PrintTarget Shutdown Test".equals(baos.toString()));
    }
}
