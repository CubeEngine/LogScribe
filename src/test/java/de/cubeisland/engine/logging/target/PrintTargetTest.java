package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.DefaultLogFactory;
import de.cubeisland.engine.logging.Log;
import de.cubeisland.engine.logging.target.format.DefaultFormat;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintTargetTest extends TestCase
{

    private DefaultLogFactory factory;

    @Override
    public void setUp() throws Exception
    {
        this.factory = new DefaultLogFactory();
    }

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
