package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.LogTarget;
import de.cubeisland.engine.logging.target.format.Format;

/**
 * An abstract FormattedTarget allowing to format a LogEntry
 * @param <F> the FormatType
 */
public abstract class FormattedTarget<F extends Format> extends LogTarget
{
    protected F format;

    protected FormattedTarget(F format)
    {
        this.format = format;
    }
}
