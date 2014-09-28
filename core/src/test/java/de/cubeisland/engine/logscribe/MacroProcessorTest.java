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
package de.cubeisland.engine.logscribe;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class MacroProcessorTest extends TestCase
{
    private MacroProcessor macroProcessor;

    @Override
    public void setUp() throws Exception
    {
        this.macroProcessor = new MacroProcessor();
    }

    public void testMacroProcessor()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", "value");
        map.put("key2", "value2");
        map.put("key}", "value3");
        assertEquals(this.macroProcessor.process("{key}{key2}", map), "valuevalue2");
        assertEquals(this.macroProcessor.process("{{key}{key2}", map), "value2");
        assertEquals(this.macroProcessor.process("\\{{key}|{key2}}", map), "{value|value2}");
        assertEquals(this.macroProcessor.process("{key\\}}", map), "value3");
        assertEquals(this.macroProcessor.process("{}{keywithoutvalue}:\\{a}\\", map), "{}:{a}\\");
    }
}
