package de.cubeisland.engine.logging.target.file.cycler;

import java.io.File;

public interface LogCycler
{
    boolean cycle(File file);
}
