package de.cubeisland.engine.logging;

import de.cubeisland.engine.logging.target.Format;

public abstract class FormattedTarget<F extends Format> extends LogTarget
{
    protected F format;

    protected FormattedTarget(F format)
    {
        this.format = format;
    }
}
