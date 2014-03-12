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

import java.util.Deque;
import java.util.LinkedList;

public abstract class Filterable
{
    protected Deque<LogFilter> filters = new LinkedList<LogFilter>();
    protected LogLevel level = LogLevel.ALL;

    public final void prependFilter(LogFilter filter)
    {
        this.filters.addFirst(filter);
    }

    public final void appendFilter(LogFilter filter)
    {
        this.filters.addLast(filter);
    }

    public final void removeFilter(LogFilter filter)
    {
        this.filters.remove(filter);
    }


    public final void removeFirstFilter()
    {
        this.filters.removeFirst();
    }

    public final void removeLastFilter()
    {
        this.filters.removeFirst();
    }

    public void setLevel(LogLevel level)
    {
        this.level = level;
    }

    public LogLevel getLevel()
    {
        return level;
    }

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

    protected abstract void publish(LogEntry entry);
}
