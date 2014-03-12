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

import java.util.HashMap;
import java.util.Map;

public class LogLevel implements Comparable<LogLevel>
{
    private static Map<String, LogLevel> map = new HashMap<String, LogLevel>();

    // meta levels
    public static final LogLevel ALL = new LogLevel("ALL", Integer.MIN_VALUE);
    public static final LogLevel NONE = new LogLevel("NONE", Integer.MAX_VALUE);

    // default levels
    public static final LogLevel TRACE = new LogLevel("TRACE", 100);
    public static final LogLevel DEBUG = new LogLevel("DEBUG", 200);
    public static final LogLevel INFO = new LogLevel("INFO", 300);
    public static final LogLevel WARN = new LogLevel("WARN", 400);
    public static final LogLevel ERROR = new LogLevel("ERROR", 500);

    private final String name;
    private final int priority;

    public LogLevel(String name, int priority)
    {
        this.name = name;
        this.priority = priority;
        map.put(name.toUpperCase(), this);
    }

    public String getName()
    {
        return name;
    }

    public int getPriority()
    {
        return priority;
    }

    public int compareTo(LogLevel o)
    {
        if (this.getPriority() < o.getPriority())
        {
            return -1;
        }
        else if (this.getPriority() > o.getPriority())
        {
            return 1;
        }
        return 0;
    }

    public static LogLevel toLevel(String name)
    {
        return map.get(name.toUpperCase());
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof LogLevel && this.compareTo((LogLevel)obj) == 0;
    }
}
