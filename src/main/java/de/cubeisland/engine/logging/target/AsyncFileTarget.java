package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.FormattedTarget;
import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.target.file.cycler.LogCycler;
import de.cubeisland.engine.logging.target.file.format.FileFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class AsyncFileTarget extends FormattedTarget<FileFormat>
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

    private final Runnable closeCallBack = new Runnable()
    {
        public void run()
        {
            close();
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

    public File getFile()
    {
        return this.file;
    }

    public boolean isAppend()
    {
        return append;
    }

    private BufferedWriter open()
    {
        if (this.cycler != null)
        {
            this.file = this.cycler.cycle(this.file, closeCallBack);
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
        }
        return writer;
    }

    private void publish0()
    {
        BufferedWriter writer = this.open();
        while (!this.queue.isEmpty())
        {
            LogEntry poll = queue.poll();
            StringBuilder sb = new StringBuilder();
            this.format.writeEntry(poll, sb);
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

    private void close()
    {
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
        {} // TODO handle me
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
}
