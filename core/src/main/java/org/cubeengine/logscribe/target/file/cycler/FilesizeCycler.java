package org.cubeengine.logscribe.target.file.cycler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

/**
 * Log cycling based on FileSize
 */
public class FilesizeCycler extends BasicCycler
{
    private long bytes;

    public FilesizeCycler(long bytes)
    {
        this(bytes, BasicCycler.DEFAULT_LINE_FORMAT, BasicCycler.DEFAULT_DATE_FORMAT);
    }

    public FilesizeCycler(long bytes, String format, DateTimeFormatter dateFormat)
    {
        super(format, dateFormat);
        this.bytes = bytes;
    }

    @Override
    public Path cycle(Path path, CloseCallback closeCallBack)
    {
        try
        {
            if (Files.exists(path) && Files.size(path) >= bytes)
            {
                return super.cycle(path, closeCallBack);
            }
            return path;
        }
        catch (IOException e)
        {
            throw new LogCyclerException(e);
        }
    }
}
