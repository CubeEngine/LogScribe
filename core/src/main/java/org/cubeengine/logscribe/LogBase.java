/*
 * The MIT License
 * Copyright © 2013 Cube Island
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cubeengine.logscribe;

import java.time.ZonedDateTime;

/**
 * LogBase provides various logging methods and redirects them to {@link Filterable#log(LogEntry)}
 */
public abstract class LogBase extends Filterable
{
    private static final Object[] NO_ARGS = {};

    /**
     * Log a message, with no arguments.
     * <p/>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param level   the level to log with
     * @param message the message to log
     */
    public void log(LogLevel level, String message)
    {
        this.log(level, message, NO_ARGS);
    }

    /**
     * Log a message, with associated Throwable information.
     * <p/>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param level     the level to log with
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void log(LogLevel level, Throwable throwable, String message)
    {
        this.log(level, throwable, message, NO_ARGS);
    }

    /**
     * Log a message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param level     the level to log with
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void log(LogLevel level, String message, Object... arguments)
    {
        this.log(level, null, message, arguments);
    }

    /**
     * Log a message, with an array of object arguments and associated Throwable information.
     * <p/>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param level     the level to log with
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void log(LogLevel level, Throwable throwable, String message, Object... arguments)
    {
        // this is copied from log(LogEntry) to prevent unnecessary object creation
        if (level.compareTo(this.level) < 0)
        {
            return;
        }

        this.log(new LogEntry(level, throwable, message, arguments, ZonedDateTime.now()));
    }

    /**
     * Log a TRACE message.
     * <p/>
     * If the logger is currently enabled for the TRACE message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void trace(String message)
    {
        this.trace(message, NO_ARGS);
    }

    /**
     * Log a TRACE message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the TRACE message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void trace(Throwable throwable, String message)
    {
        this.trace(throwable, message, NO_ARGS);
    }

    /**
     * Log a TRACE message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the TRACE message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void trace(String message, Object... arguments)
    {
        this.log(LogLevel.TRACE, message, arguments);
    }

    /**
     * Log a TRACE message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the TRACE message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void trace(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.TRACE, throwable, message, arguments);
    }

    /**
     * Log a DEBUG message.
     * <p/>
     * If the logger is currently enabled for the DEBUG message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void debug(String message)
    {
        this.debug(message, NO_ARGS);
    }

    /**
     * Log a DEBUG message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the DEBUG message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void debug(Throwable throwable, String message)
    {
        this.debug(throwable, message, NO_ARGS);
    }

    /**
     * Log a DEBUG message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the DEBUG message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void debug(String message, Object... arguments)
    {
        this.log(LogLevel.DEBUG, message, arguments);
    }

    /**
     * Log a DEBUG message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the DEBUG message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void debug(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.DEBUG, throwable, message, arguments);
    }

    /**
     * Log a INFO message.
     * <p/>
     * If the logger is currently enabled for the INFO message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void info(String message)
    {
        this.info(message, NO_ARGS);
    }

    /**
     * Log a INFO message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the INFO message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void info(Throwable throwable, String message)
    {
        this.info(throwable, message, NO_ARGS);
    }

    /**
     * Log a INFO message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the INFO message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void info(String message, Object... arguments)
    {
        this.log(LogLevel.INFO, message, arguments);
    }

    /**
     * Log a INFO message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the INFO message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void info(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.INFO, throwable, message, arguments);
    }

    /**
     * Log a NOTICE message.
     * <p/>
     * If the logger is currently enabled for the NOTICE message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void notice(String message)
    {
        this.notice(message, NO_ARGS);
    }

    /**
     * Log a NOTICE message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the NOTICE message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void notice(Throwable throwable, String message)
    {
        this.notice(throwable, message, NO_ARGS);
    }

    /**
     * Log a NOTICE message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the NOTICE message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void notice(String message, Object... arguments)
    {
        this.log(LogLevel.WARNING, message, arguments);
    }

    /**
     * Log a NOTICE message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the NOTICE message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void notice(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.WARNING, throwable, message, arguments);
    }

    /**
     * Log a WARNING message.
     * <p/>
     * If the logger is currently enabled for the WARNING message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void warn(String message)
    {
        this.warn(message, NO_ARGS);
    }

    /**
     * Log a WARNING message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the WARNING message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void warn(Throwable throwable, String message)
    {
        this.warn(throwable, message, NO_ARGS);
    }

    /**
     * Log a WARNING message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the WARNING message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void warn(String message, Object... arguments)
    {
        this.log(LogLevel.WARNING, message, arguments);
    }

    /**
     * Log a WARNING message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the WARNING message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void warn(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.WARNING, throwable, message, arguments);
    }

    /**
     * Log a ERROR message.
     * <p/>
     * If the logger is currently enabled for the ERROR message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void error(String message)
    {
        this.error(message, NO_ARGS);
    }

    /**
     * Log a ERROR message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the ERROR message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void error(Throwable throwable, String message)
    {
        this.error(throwable, message, NO_ARGS);
    }

    /**
     * Log a ERROR message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the ERROR message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void error(String message, Object... arguments)
    {
        this.log(LogLevel.ERROR, message, arguments);
    }

    /**
     * Log a ERROR message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the ERROR message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void error(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.ERROR, throwable, message, arguments);
    }

    /**
     * Log a CRITICAL message.
     * <p/>
     * If the logger is currently enabled for the CRITICAL message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void crit(String message)
    {
        this.crit(message, NO_ARGS);
    }

    /**
     * Log a CRITICAL message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the CRITICAL message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void crit(Throwable throwable, String message)
    {
        this.crit(throwable, message, NO_ARGS);
    }

    /**
     * Log a CRITICAL message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the CRITICAL message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void crit(String message, Object... arguments)
    {
        this.log(LogLevel.ERROR, message, arguments);
    }

    /**
     * Log a CRITICAL message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the CRITICAL message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void crit(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.ERROR, throwable, message, arguments);
    }

    /**
     * Log a FATAL message.
     * <p/>
     * If the logger is currently enabled for the FATAL message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void fatal(String message)
    {
        this.fatal(message, NO_ARGS);
    }

    /**
     * Log a FATAL message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the FATAL message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void fatal(Throwable throwable, String message)
    {
        this.fatal(throwable, message, NO_ARGS);
    }

    /**
     * Log a FATAL message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the FATAL message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void fatal(String message, Object... arguments)
    {
        this.log(LogLevel.ERROR, message, arguments);
    }

    /**
     * Log a FATAL message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the FATAL message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void fatal(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.ERROR, throwable, message, arguments);
    }

    /**
     * Log a EMERG message.
     * <p/>
     * If the logger is currently enabled for the EMERG message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message the message to log
     */
    public void emerg(String message)
    {
        this.emerg(message, NO_ARGS);
    }

    /**
     * Log a EMERG message, with associated Throwable information
     * <p/>
     * If the logger is currently enabled for the EMERG message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     */
    public void emerg(Throwable throwable, String message)
    {
        this.emerg(throwable, message, NO_ARGS);
    }

    /**
     * Log a EMERG message, with an array of object arguments.
     * <p/>
     * If the logger is currently enabled for the EMERG message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void emerg(String message, Object... arguments)
    {
        this.log(LogLevel.ERROR, message, arguments);
    }

    /**
     * Log a EMERG message, with an array of object arguments and associated Throwable information
     * <p/>
     * If the logger is currently enabled for the EMERG message
     * level then the given message is forwarded to all the
     * registered LogTarget objects.
     *
     * @param throwable the Throwable associated with this log message
     * @param message   the message to log
     * @param arguments the array of arguments for the message
     */
    public void emerg(Throwable throwable, String message, Object... arguments)
    {
        this.log(LogLevel.ERROR, throwable, message, arguments);
    }
}
