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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The default LogFactory implementation
 */
public class DefaultLogFactory implements LogFactory
{
    private final Map<Class<?>, Map<String, Log>> logs;

    /**
     * Creates a new LogFactory Instance
     */
    public DefaultLogFactory()
    {
        this.logs = new ConcurrentHashMap<>();
    }

    public Log getLog(Class<?> clazz, String id)
    {
        Map<String, Log> logMap = this.logs.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>());
        return logMap.computeIfAbsent(id, i -> new Log(this, clazz, i));
    }

    public Log getLog(Class<?> clazz)
    {
        return this.getLog(clazz, clazz.getName());
    }

    public void shutdown()
    {
        for (Map<String, Log> stringLogMap : this.logs.values())
        {
            for (Log log : stringLogMap.values())
            {
                log.shutdown();
            }
        }
        this.logs.clear();
    }

    public void remove(Log log)
    {
        this.logs.get(log.getClazz()).remove(log.getId());
    }
}
