package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogTarget;
import de.cubeisland.engine.logging.target.file.cycler.LogCycler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileTarget extends LogTarget
{
    private final File file;
    private final boolean append;
    private final boolean blocking;
    private LogCycler cycler;

    public FileTarget(File file)
    {
        this(file, true);
    }

    public FileTarget(File file, boolean append)
    {
        this(file, append, false);
    }

    public FileTarget(File file, boolean append, boolean blocking)
    {
        this.file = file;
        this.append = append;
        this.blocking = blocking;
    }

    public File getFile()
    {
        return this.file;
    }

    public boolean isAppend()
    {
        return append;
    }

    public boolean isBlocking()
    {
        return this.blocking;
    }

    public LogCycler getCycler()
    {
        return cycler;
    }

    public void setCycler(LogCycler cycler)
    {
        this.cycler = cycler;
    }

    private void open()
    {
        try
        {
            LogCycler cycler = this.getCycler();
            if (cycler != null)
            {
                cycler.cycle(this.file);
            }
            FileOutputStream os = new FileOutputStream(this.file, this.append);
            FileChannel ch = os.getChannel();
            // TODO implement me
        }
        catch (FileNotFoundException e)
        {}
    }

    public void store(LogEntry entry)
    {
        // TODO implement me
    }
}
