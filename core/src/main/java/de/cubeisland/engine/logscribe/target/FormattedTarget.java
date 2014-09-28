package de.cubeisland.engine.logscribe.target;

import de.cubeisland.engine.logscribe.LogTarget;
import de.cubeisland.engine.logscribe.target.format.Format;

/**
 * An abstract FormattedTarget providing a formatter for implementations
 *
 * @param <F> the FormatType
 */
public abstract class FormattedTarget<F extends Format> extends LogTarget
{
    private F format;

    /**
     * Creates a new FormattedTarget
     *
     * @param format the Format
     */
    protected FormattedTarget(F format)
    {
        this.format = format;
    }

    public void setFormat(F format) {
        this.format = format;
    }

    public F getFormat() {
        return this.format;
    }
}
