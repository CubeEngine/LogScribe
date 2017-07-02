package de.cubeisland.engine.logscribe.target.file.cycler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Cycles based on log file age. Should only be used on file systems that track birth time!
 */
public class DateCycler extends BasicCycler
{
    private final TemporalAmount maxAge;

    public DateCycler(TemporalAmount maxAge)
    {
        this(maxAge, BasicCycler.DEFAULT_LINE_FORMAT, BasicCycler.DEFAULT_DATE_FORMAT);
    }

    public DateCycler(TemporalAmount maxAge, String format, DateTimeFormatter dateFormat)
    {
        super(format, dateFormat);
        this.maxAge = maxAge;
    }

    @Override
    public Path cycle(Path path, CloseCallback closeCallBack)
    {
        try
        {
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            Instant birthTime = attributes.creationTime().toInstant();
            Instant now = Instant.now();

            Duration age = Duration.between(birthTime, now).abs();
            if (age.get(SECONDS) > maxAge.get(SECONDS))
            {
                return super.cycle(path, closeCallBack);
            }
        }
        catch (IOException e)
        {
            throw new LogCyclerException(e);
        }
        return path;
    }
}
