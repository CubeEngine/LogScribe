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

import java.util.Map;

/**
 * A simple macro-processor
 */
public class MacroProcessor
{
    private static final char MACRO_BEGIN = '{';
    private static final char MACRO_END = '}';
    private static final char ESCAPE = '\\';

    /**
     * Processes named macros in a message.
     * <p>e.g.: Replaces {key} with the value of "key" in arguments.
     * Macros names will be intern'ed by the parser, so IdentityHashMaps can be used.
     *
     * @param message the message
     * @param macros  the arguments
     *
     * @return the processed message
     */
    public static String processMacros(String message, Map<String, Object> macros)
    {
        if (message.isEmpty())
        {
            return message;
        }
        StringBuilder out = new StringBuilder(message.length() * 2);

        int i = 0;
        final int len = message.length();
        char current;
        boolean expectPlain = false;

        while (i < len)
        {
            current = message.charAt(i);

            if (!expectPlain && current == MACRO_BEGIN)
            {
                final int start = i + 1;
                final int end = message.indexOf(MACRO_END, start);
                if (end == -1 || end == start)
                {
                    expectPlain = true;
                }
                else
                {
                    String name = message.substring(start, end).intern();
                    Object arg = macros.get(name);
                    if (arg != null)
                    {
                        out.append(arg);
                        i = end + 1;
                    }
                    else
                    {
                        expectPlain = true;
                    }
                }
            }
            else
            {
                int start = i;
                if (expectPlain)
                {
                    ++i;
                }
                expectPlain = false;
                boolean escaped = false;
                while (i < message.length())
                {
                    current = message.charAt(i);
                    if (current == ESCAPE && i + 1 < message.length())
                    {
                        i++;
                        current = message.charAt(i++);
                        escaped = current == ESCAPE || current == MACRO_BEGIN;
                    }
                    else if (current == MACRO_BEGIN)
                    {
                        break;
                    }
                    else
                    {
                        ++i;
                    }
                }

                if (escaped)
                {
                    unescape(message, start, i, out);
                }
                else
                {
                    out.append(message, start, i);
                }
            }
        }

        return out.toString();
    }

    /**
     * Processes simple {} macros by inserting values from args with the matching index.
     * If more macros than arguments exist, additional macros will not be replaced.
     *
     * @param input the message
     * @param args  the arguments
     *
     * @return the processed message
     */
    public static String processSimpleMacros(String input, Object[] args)
    {
        if (input.isEmpty() || args.length == 0)
        {
            return input;
        }

        StringBuilder out = new StringBuilder();

        int stringIndex = 0;
        int argIndex = 0;
        int len = input.length();
        char current;
        char next;

        while (stringIndex < len)
        {
            current = input.charAt(stringIndex);
            if (current == ESCAPE && stringIndex + 1 < len)
            {
                next = input.charAt(stringIndex + 1);
                if (next == ESCAPE || next == MACRO_BEGIN)
                {
                    stringIndex++;
                    out.append(next);
                }
                else
                {
                    out.append(current);
                }
            }
            else if (current == MACRO_BEGIN && stringIndex + 1 < len)
            {
                next = input.charAt(stringIndex + 1);
                if (next == MACRO_END && argIndex < args.length)
                {
                    stringIndex++;
                    out.append(args[argIndex++]);
                }
                else
                {
                    out.append(current);
                }
            }
            else
            {
                out.append(current);
            }

            stringIndex++;
        }

        return out.toString();
    }

    private static void unescape(String input, int start, int end, StringBuilder out)
    {
        char current, next;
        for (int i = start; i < end; ++i)
        {
            current = input.charAt(i);
            if (current == ESCAPE && i + 1 < end)
            {
                next = input.charAt(++i);
                if (next == ESCAPE || next == MACRO_BEGIN)
                {
                    out.append(next);
                }
                else
                {
                    out.append(current).append(next);
                }
            }
            else
            {
                out.append(current);
            }
        }
    }
}
