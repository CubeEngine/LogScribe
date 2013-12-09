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

import de.cubeisland.engine.logging.target.proxy.LogProxyTarget;

import java.util.Date;
import java.util.LinkedList;

public class Log extends LogBase
{
    private final LogFactory factory;
    private final String id;
    private final Class<?> clazz;
    private final Date birthdate = new Date(System.currentTimeMillis());

    private final LinkedList<LogTarget> targets = new LinkedList<LogTarget>();

    public Log(LogFactory factory, Class<?> clazz, String id)
    {
        this.factory = factory;
        this.id = id;
        this.clazz = clazz;
    }

    Class<?> getClazz()
    {
        return this.clazz;
    }

    public String getId()
    {
        return id;
    }
    public Date getBirthdate()
    {
        return birthdate;
    }

    public Log addTarget(LogTarget target)
    {
        this.targets.add(target);
        return this;
    }

    public LogTarget addDelegate(Log log)
    {
        LogProxyTarget logProxyTarget = new LogProxyTarget(log);
        this.addTarget(logProxyTarget);
        return logProxyTarget;
    }

    public Log removeTarget(LogTarget target)
    {
        this.targets.remove(target);
        return this;
    }

    @Override
    protected void publish(LogEntry entry)
    {
        if (!this.targets.isEmpty())
        {
            for (LogTarget target : this.targets)
            {
                target.log(entry.copy());
            }
        }
    }

    public void shutdown()
    {
        this.factory.shutdown(this);
    }

    void shutdown0()
    {
        for (LogTarget target : this.targets)
        {
            target.shutdown();
        }
        // TODO
    }
}
