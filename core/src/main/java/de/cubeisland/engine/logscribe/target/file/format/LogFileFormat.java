package de.cubeisland.engine.logscribe.target.file.format;

import de.cubeisland.engine.logscribe.target.format.DefaultFormat;

import java.text.DateFormat;
import java.util.Date;

/**
 * A Simple Plain Text LogFileFormat
 */
public class LogFileFormat extends DefaultFormat implements FileFormat
{
    public LogFileFormat() {
        super();
    }

    public LogFileFormat(String format) {
        super(format);
    }

    public LogFileFormat(String format, DateFormat dateFormat) {
        super(format, dateFormat);
    }

    public void writeHeader(StringBuilder builder)
    {
        builder.append("Logging Start: ").append(this.dateFormat.format(new Date(System.currentTimeMillis()))).append("\n");
    }

    public void writeTrailer(StringBuilder builder)
    {
        builder.append("Logging End: ").append(this.dateFormat.format(new Date(System.currentTimeMillis()))).append("\n");
    }
}
