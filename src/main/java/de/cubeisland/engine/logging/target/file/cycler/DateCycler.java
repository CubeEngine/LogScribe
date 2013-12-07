package de.cubeisland.engine.logging.target.file.cycler;

import java.io.File;

public class DateCycler implements LogCycler
{
    public File cycle(File file, Runnable closeCallBack)
    {
        return file;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
