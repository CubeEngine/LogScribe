package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.LogTarget;
import de.cubeisland.engine.logging.target.format.Format;

/**
 * An abstract FormattedTarget providing a formatter for implementations
 *
 * @param <F> the FormatType
 */
public abstract class FormattedTarget<F extends Format> extends LogTarget
{
    protected F format;

    /**
     * Creates a new FormattedTarget
     *
     * @param format the Format
     */
    protected FormattedTarget(F format)
    {
        this.format = format;
    }
}
