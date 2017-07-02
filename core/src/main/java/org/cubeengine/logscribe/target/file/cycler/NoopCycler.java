package org.cubeengine.logscribe.target.file.cycler;

import java.nio.file.Path;

public class NoopCycler implements LogCycler
{
    public static final LogCycler NOOP = new NoopCycler();

    @Override
    public Path cycle(Path file, CloseCallback closeCallBack)
    {
        return file;
    }
}
