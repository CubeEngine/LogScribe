package org.cubeengine.logscribe.target.file.cycler;

import org.cubeengine.logscribe.target.LogTargetException;

public class LogCyclerException extends LogTargetException
{
    public LogCyclerException(String message)
    {
        super(message);
    }

    public LogCyclerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public LogCyclerException(Throwable cause)
    {
        super(cause);
    }
}
