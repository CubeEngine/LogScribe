package org.cubeengine.logscribe.target.file.format;

import org.cubeengine.logscribe.LogEntry;

// TODO implement me
public class CSVFileFormat implements FileFormat
{
    public void writeHeader(StringBuilder builder)
    {
        builder.append("#a,b,c");
    }

    public void writeEntry(LogEntry logEntry, StringBuilder builder)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeTrailer(StringBuilder builder)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
