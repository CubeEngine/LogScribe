package de.cubeisland.engine.logscribe.target.file.format;

import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import de.cubeisland.engine.logscribe.target.format.DefaultFormat;

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

    public LogFileFormat(String format, DateTimeFormatter dateFormat) {
        super(format, dateFormat);
    }

    public void writeHeader(StringBuilder builder)
    {
        builder.append("Logging Start: ").append(this.dateFormat.format(ZonedDateTime.now())).append("\n");
    }

    public void writeTrailer(StringBuilder builder)
    {
        builder.append("Logging End: ").append(this.dateFormat.format(ZonedDateTime.now())).append("\n");
    }
}
