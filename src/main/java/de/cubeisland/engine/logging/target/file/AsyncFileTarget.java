package de.cubeisland.engine.logging.target.file;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.target.FormattedTarget;
import de.cubeisland.engine.logging.target.file.cycler.CloseCallback;
import de.cubeisland.engine.logging.target.file.cycler.LogCycler;
import de.cubeisland.engine.logging.target.file.format.FileFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncFileTarget extends FormattedTarget<FileFormat> implements CloseCallback
{
    private File file;
    private final boolean append;
    private final LogCycler cycler;

    private ExecutorService executor;
    private final ThreadFactory threadFactory;

    private final Runnable runner = new Runnable()
    {
        public void run()
        {
            publish0();
        }
    };

    private final ConcurrentLinkedQueue<LogEntry> queue = new ConcurrentLinkedQueue<LogEntry>();
    private Future<?> future;

    public AsyncFileTarget(File file, FileFormat format)
    {
        this(file, format, true);
    }

    public AsyncFileTarget(File file, FileFormat format, boolean append)
    {
        this(file, format, append, null);
    }

    public AsyncFileTarget(File file, FileFormat format, boolean append, LogCycler cycler)
    {
        this(file, format, append, cycler, null);
    }

    public AsyncFileTarget(File file, FileFormat format, boolean append, LogCycler cycler, ThreadFactory threadFactory)
    {
        super(format);
        this.file = file;
        this.append = append;
        this.threadFactory = threadFactory;
        this.cycler = cycler;
    }

    /**
     * Returns the File this FileTarget writes into
     *
     * @return the file
     */
    public File getFile()
    {
        return this.file;
    }

    /**
     * Returns true if this FileTarget appends to the previous file
     *
     * @return false if the previous file is not appended
     */
    public boolean isAppend()
    {
        return append;
    }

    private BufferedWriter open()
    {
        if (this.cycler != null)
        {
            this.file = this.cycler.cycle(this.file, this);
        }
        return this.getWriter();
    }

    private BufferedWriter writer = null;

    private BufferedWriter getWriter()
    {
        if (writer == null)
        {
            FileOutputStream fos = null;
            try
            {
                fos = new FileOutputStream(this.file, this.append);
            }
            catch (FileNotFoundException e)
            {
                // TODO handle me (directory missing?)
            }
            // TODO FileLock
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            this.writer = new BufferedWriter(osw);
            StringBuilder sb = new StringBuilder();
            this.format.writeHeader(sb);
            if (!sb.toString().isEmpty())
            {
                try
                {
                    writer.write(sb.toString());
                }
                catch (IOException e)
                {
                    // TODO handle me
                }
            }

        }
        return writer;
    }

    private void publish0()
    {
        BufferedWriter bWriter = this.open();
        while (!this.queue.isEmpty())
        {
            LogEntry poll = queue.poll();
            StringBuilder sb = new StringBuilder();
            this.format.writeEntry(poll, sb);
            try
            {
                bWriter.write(sb.toString());
            }
            catch (IOException e)
            {
                // TODO handle me
            }
        }
        try
        {
            bWriter.flush();
        }
        catch (IOException e)
        {
            // TODO handle me
        }
    }

    public void close()
    {
        if (writer == null)
        {
            return;
        }
        try
        {
            BufferedWriter writer = this.getWriter();
            StringBuilder sb = new StringBuilder();
            this.format.writeTrailer(sb);
            writer.write(sb.toString());
            writer.close();
            this.writer = null;
        }
        catch (IOException e)
        {
            // TODO handle me
        }
    }

    private void publish()
    {
        if (this.future == null || this.future.isDone())
        {
            this.future = this.getExecutor().submit(runner);
        }
        // else currently running IO
    }

    protected void publish(LogEntry entry)
    {
        this.queue.add(entry);
        this.publish();
    }

    private ExecutorService getExecutor()
    {
        if (executor == null)
        {
            if (threadFactory == null)
            {
                this.executor = Executors.newSingleThreadExecutor();
            }
            else
            {
                this.executor = Executors.newSingleThreadExecutor(threadFactory);
            }
        }
        return executor;
    }

    @Override
    protected void shutdown0()
    {
        if (!(this.future == null || this.future.isDone()))
        {
            try
            {
                future.get(5, TimeUnit.SECONDS); // check for logging queue to empty
            }
            catch (InterruptedException e)
            {
            }
            catch (ExecutionException e)
            {
            }
            catch (TimeoutException e)
            {
            }
            // TODO handle Exceptions
        }
        this.close();
    }
}
