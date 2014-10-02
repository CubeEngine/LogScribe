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

import de.cubeisland.engine.logscribe.target.proxy.LogProxyTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * A Logger with a list of targets used to publish LogEntries
 */
public class Log extends LogBase implements Flushable
{
    private final LogFactory factory;
    private final String id;
    private final Class<?> clazz;
    private final Date birthDate = new Date(System.currentTimeMillis());

    private final List<LogTarget> targets = new ArrayList<LogTarget>();
    private boolean isShutdown = false;

    Log(LogFactory factory, Class<?> clazz, String id)
    {
        this.factory = factory;
        this.id = id;
        this.clazz = clazz;
    }

    /**
     * Returns the Class this Logger was created for
     *
     * @return the class this Logger was created for
     */
    Class<?> getClazz()
    {
        return this.clazz;
    }

    /**
     * Returns the id of this logger
     *
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Returns the creation date of this Logger
     *
     * @return the creation date if this Logger
     */
    public Date getBirthDate()
    {
        return new Date(birthDate.getTime());
    }

    /**
     * Adds a LogTarget
     *
     * @param target the target
     *
     * @return fluent interface
     */
    public Log addTarget(LogTarget target)
    {
        this.targets.add(target);
        return this;
    }

    /**
     * Adds a ProxyTarget for an other Logger
     *
     * @param log the logger to delegate to
     *
     * @return the created LogTarget
     */
    public LogTarget addDelegate(Log log)
    {
        LogProxyTarget logProxyTarget = new LogProxyTarget(log);
        this.addTarget(logProxyTarget);
        return logProxyTarget;
    }

    /**
     * Removes a LogTarget
     *
     * @param target the target to remove
     *
     * @return fluent interface
     */
    public Log removeTarget(LogTarget target)
    {
        this.targets.remove(target);
        return this;
    }

    @Override
    protected void publish(LogEntry entry)
    {
        if (this.isShutdown)
        {
            return;
        }
        if (!this.targets.isEmpty())
        {
            for (LogTarget target : this.targets)
            {
                target.log(entry.copy());
            }
        }
    }

    /**
     * Shuts down this Logger and all its LogTargets
     * <p>The LogFactory will no longer return this Logger for its id
     */
    public void shutdown()
    {
        if (this.isShutdown)
        {
            return;
        }
        this.isShutdown = true;
        for (LogTarget target : this.targets)
        {
            target.shutdown();
        }
        this.factory.remove(this);
    }

    /**
     * Returns a unmodifiable List of the targets
     *
     * @return
     */
    public List<LogTarget> getTargets()
    {
        return Collections.unmodifiableList(targets);
    }

    /**
     * Returns true if this Logger got shut down
     *
     * @return
     */
    public boolean isShutdown()
    {
        return isShutdown;
    }

    public void flush()
    {
        for (LogTarget target : this.targets)
        {
            if (target instanceof Flushable)
            {
                ((Flushable) target).flush();
            }
        }
    }
}
