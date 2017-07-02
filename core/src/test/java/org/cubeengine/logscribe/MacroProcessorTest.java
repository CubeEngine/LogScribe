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

import java.util.IdentityHashMap;
import java.util.Map;
import org.junit.Test;

import static org.cubeengine.logscribe.MacroProcessor.processMacros;
import static org.cubeengine.logscribe.MacroProcessor.processSimpleMacros;
import static org.junit.Assert.assertEquals;

public class MacroProcessorTest
{
    @Test
    public void testProcessMacros()
    {
        Map<String, Object> map = new IdentityHashMap<>();
        map.put("key", "value");
        map.put("key2", "value2");
        map.put("key}", "value3");
        assertEquals("valuevalue2", processMacros("{key}{key2}", map));
        assertEquals("{valuevalue2", processMacros("{{key}{key2}", map));
        assertEquals("{value|value2}", processMacros("\\{{key}|{key2}}", map));
        assertEquals("{}{keywithoutvalue}:{a}\\", processMacros("{}{keywithoutvalue}:\\{a}\\", map));
    }

    @Test
    public void testProcessSimpleMacros()
    {
        Object[] args = {"a", 2, 3d};

        assertEquals("a2", processSimpleMacros("{}{}", args));
        assertEquals("{a2", processSimpleMacros("{{}{}", args));
        assertEquals("{}a|2}", processSimpleMacros("\\{}{}|{}}", args));
        assertEquals("a2\\", processSimpleMacros("{}{}\\", args));
        assertEquals("a23.0{}", processSimpleMacros("{}{}{}{}", args));
    }
}
