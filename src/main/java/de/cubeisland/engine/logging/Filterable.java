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

import de.cubeisland.engine.logging.filter.LogFilter;

import java.util.LinkedList;

/**
 * Base class for Log(LogBase) and LogTarget providing Filter and LogLevel logic
 */
public abstract class Filterable
{
    protected LinkedList<LogFilter> filters = new LinkedList<LogFilter>();
    protected LogLevel level = LogLevel.ALL;

    /**
     * Adds a Filter at the front of the filter list
     *
     * @param filter the filter to apply first
     */
    public final void prependFilter(LogFilter filter)
    {
        this.filters.addFirst(filter);
    }

    /**
     * Adds a Filter at the end of the filter list
     *
     * @param filter the filter to apply last
     */
    public final void appendFilter(LogFilter filter)
    {
        this.filters.addLast(filter);
    }

    /**
     * Removes a Filter
     *
     * @param filter the filter to remove
     *
     * @return whether the given Filter wass in the filter list
     */
    public final boolean removeFilter(LogFilter filter)
    {
        return this.filters.remove(filter);
    }

    /**
     * Removes the first Filter in the filter list
     */
    public final void removeFirstFilter()
    {
        this.filters.removeFirst();
    }

    /**
     * Removes the last Filter in the filter list
     */
    public final void removeLastFilter()
    {
        this.filters.removeFirst();
    }

    /**
     * Sets the LogLevel of this Filterable
     *
     * @param level the level to set
     */
    public void setLevel(LogLevel level)
    {
        this.level = level;
    }

    /**
     * Returns the LogLevel of this Filterable
     *
     * @return the LogLevel of this Filterable
     */
    public LogLevel getLevel()
    {
        return level;
    }

    /**
     * Checks if the LogLevel is higher than the set LogLevel and all registered Filters pass and then publishes the LogEntry
     *
     * @param entry the logEntry to log
     */
    public void log(final LogEntry entry)
    {
        if (entry.getLevel().compareTo(this.level) < 0)
        {
            return;
        }
        if (!this.filters.isEmpty())
        {
            for (LogFilter filter : this.filters)
            {
                if (!filter.accept(entry))
                {
                    return;
                }
            }
        }
        this.publish(entry);
    }

    /**
     * Publishes a LogEntry
     * <p>The LogEntry may get further filtered in LogTargets
     *
     * @param entry the logEntry to publish
     */
    protected abstract void publish(LogEntry entry);
}
