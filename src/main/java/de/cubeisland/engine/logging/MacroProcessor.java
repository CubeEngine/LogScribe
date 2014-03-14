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

import java.util.Map;

/**
 * A simple macro-processor
 */
public class MacroProcessor
{
    private static final char MACRO_BEGIN = '{';
    private static final char MACRO_END = '}';
    private static final char MACRO_ESCAPE = '\\';

    /**
     * Processes Macros in a message
     * <p>e.g.: Replaces {key} with the value of "key" in arguments
     *
     * @param message the message
     * @param arguments the arguments
     * @return the processed message
     */
    public String process(String message, Map<String, Object> arguments)
    {
        StringBuilder finalString = new StringBuilder();
        StringBuilder keyBuffer = null;
        StringBuilder curBuilder = finalString;
        boolean escape = false;
        char[] chars = message.toCharArray();
        for (char curChar : chars)
        {
            if (curChar == MACRO_ESCAPE)
            {
                if (escape)
                {
                    curBuilder.append(curChar);
                    escape = false;
                }
                else
                {
                    escape = true;
                }
            }
            else if (curChar == MACRO_BEGIN)
            {
                if (curBuilder == keyBuffer)
                {
                    // macro begin in macro
                    if (escape)
                    {
                        curBuilder.append(MACRO_ESCAPE);
                        escape = false;
                    }
                    curBuilder.append(curChar);
                }
                else
                {
                    // macro begin
                    if (escape)
                    {
                        curBuilder.append(curChar);
                        escape = false;
                        break;
                    }
                    keyBuffer = new StringBuilder();
                    curBuilder = keyBuffer;
                }
            }
            else if (curChar == MACRO_END)
            {
                if (curBuilder == keyBuffer)
                {
                    // macro end
                    if (escape)
                    {
                        curBuilder.append(curChar);
                        escape = false;
                        break;
                    }
                    curBuilder = finalString;
                    Object value = arguments.get(keyBuffer.toString());
                    keyBuffer = null;
                    if (value != null)
                    {
                        curBuilder.append(String.valueOf(value));
                    }
                }
                else
                {
                    // macro end outside of macro
                    if (escape)
                    {
                        curBuilder.append(MACRO_ESCAPE);
                        escape = false;
                    }
                    curBuilder.append(curChar);
                }
            }
            else
            {
                if (escape)
                {
                    curBuilder.append(MACRO_ESCAPE);
                    escape = false;
                }
                curBuilder.append(curChar);
            }
        }
        if (escape)
        {
            // if last character was escape readd
            finalString.append(MACRO_ESCAPE);
        }

        return finalString.toString();
    }
}
