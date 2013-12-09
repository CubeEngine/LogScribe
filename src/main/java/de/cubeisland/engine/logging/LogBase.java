/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Anselm Brehme, Phillip Schichtel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.cubeisland.engine.logging;

import java.util.Date;

public abstract class LogBase extends Filterable
{
    private static final Object[] NO_ARGS = {};
    public void log(LogLevel level, String message)
    {
        this.log(level, message, NO_ARGS);
    }

    public void log(LogLevel level, Throwable t, String message)
    {
        this.log(level, t, message, NO_ARGS);
    }

    public void log(LogLevel level, String message, Object... args)
    {
        this.log(level, null, message, args);
    }

    public void log(LogLevel level, Throwable t, String message, Object... args)
    {
        // this is copied from log(LogEntry) to prevent unnecessary object creation
        if (level.compareTo(this.level) < 0)
        {
            return;
        }

        this.log(new LogEntry(level, t, message, args, new Date()));
    }

    public void trace(String message)
    {
        this.trace(message, NO_ARGS);
    }

    public void trace(Throwable t, String message)
    {
        this.trace(t, message, NO_ARGS);
    }

    public void trace(String message, Object... args)
    {
        this.log(LogLevel.TRACE, message, args);
    }

    public void trace(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.TRACE, t, message, args);
    }

    public void debug(String message)
    {
        this.debug(message, NO_ARGS);
    }

    public void debug(Throwable t, String message)
    {
        this.debug(t, message, NO_ARGS);
    }

    public void debug(String message, Object... args)
    {
        this.log(LogLevel.DEBUG, message, args);
    }

    public void debug(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.DEBUG, t, message, args);
    }

    public void info(String message)
    {
        this.info(message, NO_ARGS);
    }

    public void info(Throwable t, String message)
    {
        this.info(t, message, NO_ARGS);
    }

    public void info(String message, Object... args)
    {
        this.log(LogLevel.INFO, message, args);
    }

    public void info(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.INFO, t, message, args);
    }

    public void warn(String message)
    {
        this.warn(message, NO_ARGS);
    }

    public void warn(Throwable t, String message)
    {
        this.warn(t, message, NO_ARGS);
    }

    public void warn(String message, Object... args)
    {
        this.log(LogLevel.WARN, message, args);
    }

    public void warn(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.WARN, t, message, args);
    }

    public void error(String message)
    {
        this.error(message, NO_ARGS);
    }

    public void error(Throwable t, String message)
    {
        this.error(t, message, NO_ARGS);
    }

    public void error(String message, Object... args)
    {
        this.log(LogLevel.ERROR, message, args);
    }

    public void error(Throwable t, String message, Object... args)
    {
        this.log(LogLevel.ERROR, t, message, args);
    }

}
