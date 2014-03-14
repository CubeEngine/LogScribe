package de.cubeisland.engine.logging;

/**
 * This Exception is thrown when an error occurred while Logging
 */
public class LoggingException extends RuntimeException
{
    public LoggingException()
    {
    }

    public LoggingException(String message)
    {
        super(message);
    }

    public LoggingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public LoggingException(Throwable cause)
    {
        super(cause);
    }

    public LoggingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
