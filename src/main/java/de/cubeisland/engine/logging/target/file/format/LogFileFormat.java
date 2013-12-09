package de.cubeisland.engine.logging.target.file.format;

import de.cubeisland.engine.logging.DefaultFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LogFileFormat extends DefaultFormat implements FileFormat
{
    public LogFileFormat(String format)
    {
        this(format, new SimpleDateFormat());
    }

    public LogFileFormat(String format, DateFormat dateFormat)
    {
        super(format, dateFormat);
    }

    public void writeHeader(StringBuilder builder)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeTrailer(StringBuilder builder)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
