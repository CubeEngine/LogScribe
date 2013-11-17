package de.cubeisland.engine.logging.filter;

import de.cubeisland.engine.logging.Log;
import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogFilter;

public class ExceptionFilter implements LogFilter
{
    private final Log target;
    private final boolean removeTrace;

    public ExceptionFilter(Log target, boolean removeTrace)
    {
        this.target = target;
        this.removeTrace = removeTrace;
    }

    public boolean accept(LogEntry entry)
    {
        Throwable t = entry.getThrowable();
        if (t != null)
        {
            this.target.log(entry.getLevel(), t.getLocalizedMessage(), t);
            if (this.removeTrace)
            {
                entry.setThrowable(null);
            }
            return false;
        }
        return true;
    }
}
