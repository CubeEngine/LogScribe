/**
 * The MIT License
 * Copyright (c) 2013 Cube Island
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xpath.internal.operations.Mod;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

/**
 * A LogLevel used to define if a Logger will Log a LogEntry
 */
public class LogLevel implements Comparable<LogLevel>
{
    private static final Map<String, LogLevel> map = new HashMap<String, LogLevel>();

    // meta levels
    public static final LogLevel ALL = new LogLevel("ALL", Integer.MIN_VALUE);
    public static final LogLevel NONE = new LogLevel("NONE", Integer.MAX_VALUE);

    // default levels
    public static final LogLevel TRACE    = new LogLevel("TRACE",     100);
    public static final LogLevel DEBUG    = new LogLevel("DEBUG",     200);
    public static final LogLevel INFO     = new LogLevel("INFO",      300);
    public static final LogLevel NOTICE   = new LogLevel("NOTICE",    400);
    public static final LogLevel WARNING  = new LogLevel("WARNING",   500);
    public static final LogLevel ERROR    = new LogLevel("ERROR",     600);
    public static final LogLevel CRITICAL = new LogLevel("CRITICAL",  700);
    public static final LogLevel FATAL    = new LogLevel("FATAL",     800);
    public static final LogLevel EMERG    = new LogLevel("EMERGENCY", 700);

    private final String name;
    private final int priority;

    static
    {
        for (Field field : LogLevel.class.getDeclaredFields())
        {
            if (isStatic(field.getModifiers()) && isPublic(field.getModifiers()) && LogLevel.class.isAssignableFrom(field.getType()))
            {
                try
                {
                    LogLevel level = (LogLevel)field.get(null);
                    map.put(level.getName().toUpperCase(), level);
                    String fieldName = field.getName().toUpperCase();
                    if (!map.containsKey(fieldName))
                    {
                        map.put(fieldName, level);
                    }
                }
                catch (IllegalAccessException ignored)
                {}
            }
        }
    }

    /**
     * Creates a new LogLevel with given name and priority
     *
     * @param name     the name
     * @param priority the priority
     */
    public LogLevel(String name, int priority)
    {
        this.name = name;
        this.priority = priority;
    }

    /**
     * Returns the name of this LogLevel
     *
     * @return the name of this LogLevel
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the priority of this LogLevel
     *
     * @return the priority of this LogLevel
     */
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

    /**
     * Returns the corresponding LogLevel or null if not found
     *
     * @param name the name
     *
     * @return the corresponding LogLevel or null
     */
    public static LogLevel toLevel(String name)
    {
        return map.get(name.toUpperCase());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        LogLevel logLevel = (LogLevel)o;

        if (priority != logLevel.priority)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return priority;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
