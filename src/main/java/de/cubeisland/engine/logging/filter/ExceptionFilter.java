package de.cubeisland.engine.logging.filter;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogFilter;

/**
 * Created with IntelliJ IDEA.
 * User: phillip.schichtel
 * Date: 13.11.13
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionFilter implements LogFilter
{
    public boolean accept(LogEntry entry)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
