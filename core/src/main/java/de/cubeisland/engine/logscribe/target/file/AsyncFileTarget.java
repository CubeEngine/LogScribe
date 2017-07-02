package de.cubeisland.engine.logscribe.target.file;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.concurrent.ThreadFactory;
import de.cubeisland.engine.logscribe.LogEntry;
import de.cubeisland.engine.logscribe.LoggingException;
import de.cubeisland.engine.logscribe.target.FormattedTarget;
import de.cubeisland.engine.logscribe.target.LogTargetException;
import de.cubeisland.engine.logscribe.target.file.cycler.CloseCallback;
import de.cubeisland.engine.logscribe.target.file.cycler.LogCycler;
import de.cubeisland.engine.logscribe.target.file.cycler.NoopCycler;
import de.cubeisland.engine.logscribe.target.file.format.FileFormat;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class AsyncFileTarget extends FormattedTarget<FileFormat> implements CloseCallback
{
    private static final OpenOption[] OPEN_REPLACE = {TRUNCATE_EXISTING};
    private static final OpenOption[] OPEN_APPEND = {APPEND};
    private final Object lock = new byte[0];

    private Path path;
    private final boolean append;
    private final LogCycler cycler;


    private BufferedWriter writer = null;
    private final int timeoutMillis;
    private final int timeoutNanos;
    private final BlockingRingBuffer<LogEntry> buffer;
    private Thread queueConsumer;
    private final Object shutdownMonitor = new byte[0];

    AsyncFileTarget(Path path, FileFormat format, boolean append, LogCycler cycler, ThreadFactory threadFactory, int timeoutMillis, int timeoutNanos, int capacity)
    {
        super(format);
        this.timeoutMillis = timeoutMillis;
        this.timeoutNanos = timeoutNanos;
        this.buffer = new BlockingRingBuffer<>(capacity);
        this.path = path;
        this.append = append;
        this.queueConsumer = threadFactory.newThread(this::processQueue);
        this.cycler = cycler;
    }

    private BufferedWriter open()
    {
        if (this.cycler != null)
        {
            this.path = this.cycler.cycle(this.path, this);
        }
        return this.getWriter();
    }

    private BufferedWriter getWriter()
    {
        // don't lock if we don't have to
        if (writer == null)
        {
            synchronized (this.lock)
            {
                // re-check to be sure
                if (writer == null)
                {
                    try
                    {
                        OutputStream os = Files.newOutputStream(path, this.append ? OPEN_APPEND : OPEN_REPLACE);
                        OutputStreamWriter osw = new OutputStreamWriter(os, UTF_8);
                        this.writer = new BufferedWriter(osw);
                        StringBuilder sb = new StringBuilder();
                        getFormat().writeHeader(sb);
                        if (sb.length() > 0)
                        {
                            writer.write(sb.toString());
                        }
                    }
                    catch (FileNotFoundException e)
                    {
                        throw new LoggingException(e);
                    }
                    catch (IOException e)
                    {
                        throw new LoggingException("Error while writing Header", e);
                    }
                }
            }
        }
        return writer;
    }

    private void processQueue()
    {
        try
        {
            BufferedWriter bWriter = this.open();
            StringBuilder sb = new StringBuilder(100);
            while (!isShutdown())
            {
                LogEntry poll = buffer.get(this.timeoutMillis, this.timeoutNanos);
                if (poll != null)
                {
                    sb.setLength(0);
                    getFormat().writeEntry(poll, sb);
                    bWriter.write(sb.toString());
                    bWriter.flush();
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            throw new LoggingException("Error while publishing LogEntry", e);
        }
        this.shutdownMonitor.notifyAll();
    }

    public void close()
    {
        if (writer == null)
        {
            return;
        }
        try
        {
            BufferedWriter bWriter = this.getWriter();
            StringBuilder sb = new StringBuilder();
            getFormat().writeTrailer(sb);
            if (sb.length() > 0)
            {
                bWriter.write(sb.toString());
            }
            bWriter.close();
            this.writer = null;
        }
        catch (IOException e)
        {
            throw new LoggingException("Error while writing Trailer", e);
        }
    }

    protected void publish(LogEntry entry)
    {
        try
        {
            while (!isShutdown() && !this.buffer.add(entry, timeoutMillis, timeoutNanos));
        }
        catch (InterruptedException e)
        {
            throw new LogTargetException(e);
        }
    }

    @Override
    protected void onShutdown()
    {
        try
        {
            while (this.queueConsumer.isAlive())
            {
                this.shutdownMonitor.wait();
            }
        }
        catch (InterruptedException e)
        {
            throw new LoggingException("Error while waiting to finish logging", e);
        }
        finally
        {
            this.close();
        }
    }

    public static final class Builder
    {
        private final Path path;
        private final FileFormat format;
        private ThreadFactory threadFactory = Thread::new;
        private boolean append = true;
        private LogCycler cycler = NoopCycler.NOOP;
        private int timeoutMillis = 100;
        private int timeoutNanos = 0;
        private int capacity = 100;

        public Builder(Path path, FileFormat format)
        {
            this.path = path;
            this.format = format;
        }

        public boolean isAppend()
        {
            return append;
        }

        public Builder setAppend(boolean append)
        {
            this.append = append;
            return this;
        }

        public LogCycler getCycler()
        {
            return cycler;
        }

        public Builder setCycler(LogCycler cycler)
        {
            this.cycler = cycler;
            return this;
        }

        public ThreadFactory getThreadFactory()
        {
            return threadFactory;
        }

        public Builder setThreadFactory(ThreadFactory threadFactory)
        {
            this.threadFactory = threadFactory;
            return this;
        }

        public int getTimeoutMillis()
        {
            return timeoutMillis;
        }

        public Builder setTimeoutMillis(int timeoutMillis)
        {
            this.timeoutMillis = timeoutMillis;
            return this;
        }

        public int getTimeoutNanos()
        {
            return timeoutNanos;
        }

        public Builder setTimeoutNanos(int timeoutNanos)
        {
            this.timeoutNanos = timeoutNanos;
            return this;
        }

        public int getCapacity()
        {
            return capacity;
        }

        public Builder setCapacity(int capacity)
        {
            this.capacity = capacity;
            return this;
        }

        public AsyncFileTarget build()
        {
            return new AsyncFileTarget(path, format, append, cycler, threadFactory, timeoutMillis, timeoutNanos, capacity);
        }
    }
}
