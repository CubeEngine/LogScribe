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
package de.cubeisland.engine.logscribe;

import java.util.Date;

/**
 * A LogEntry with LogLevel, Message, Arguments, Throwable and Date
 */
public class LogEntry
{
    private LogLevel level;
    private String message;
    private Object[] args;
    private Throwable throwable;
    private Date date;

    /**
     * Creates a new LogEntry
     *
     * @param level     the logLevel
     * @param throwable the throwable (can be null)
     * @param message   the message
     * @param args      the messageArguments
     * @param date      the date
     */
    public LogEntry(LogLevel level, Throwable throwable, String message, Object[] args, Date date)
    {
        this.level = level;
        this.throwable = throwable;
        this.message = message;
        this.args = args == null ? null : args.clone();
        this.date = date;
    }

    /**
     * Returns the LogLevel of this LogEntry
     *
     * @return the LogLevel
     */
    public LogLevel getLevel()
    {
        return level;
    }

    /**
     * Sets the LogLevel of this LogEntry
     *
     * @param level the logLevel to set
     */
    public void setLevel(LogLevel level)
    {
        this.level = level;
    }

    /**
     * Returns the Throwable of this LogEntry
     *
     * @return the throwable or null
     */
    public Throwable getThrowable()
    {
        return throwable;
    }

    /**
     * Sets the Throwable of this LogEntry
     *
     * @param throwable the throwable to set
     */
    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }

    /**
     * Returns the Message of this LogEntry
     *
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets the Message of this LogEntry
     *
     * @param message the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Returns true if the args array has been set and is not empty
     *
     * @return true if args are available
     */
    public boolean hasArgs()
    {
        return this.args != null && this.args.length > 0;
    }

    /**
     * Returns the Arguments of this LogEntry
     *
     * @return the arguments
     */
    public Object[] getArgs()
    {
        return args;
    }

    /**
     * Sets the Arguments of this LogEntry
     *
     * @param args the arguments
     */
    public void setArgs(Object[] args)
    {
        this.args = args == null ? null : args.clone();
    }

    /**
     * Returns the Date of this LogEntry
     *
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Creates an identical copy of this LogEntry
     *
     * @return the copy
     */
    LogEntry copy()
    {
        return new LogEntry(this.getLevel(), this.getThrowable(), this.getMessage(), this.getArgs(), this.getDate());
    }

    /**
     * Returns true if this LogEntry has a Throwable
     *
     * @return whether this LogEntry has a Throwable
     */
    public boolean hasThrowable()
    {
        return throwable != null;
    }
}
