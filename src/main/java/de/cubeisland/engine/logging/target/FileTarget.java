package de.cubeisland.engine.logging.target;

import de.cubeisland.engine.logging.LogEntry;
import de.cubeisland.engine.logging.LogTarget;
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

public class FileTarget extends LogTarget
{
    private File file;
    private final boolean append;
    private LogCycler cycler;

    private FileFormat format; // TODO set
    private ExecutorService executor;
    private ThreadFactory threadFactory;

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

    private ConcurrentLinkedQueue<LogEntry> queue = new ConcurrentLinkedQueue<LogEntry>();
    private Future<?> future;

    public FileTarget(File file)
    {
        this(file, true);
    }

    public FileTarget(File file, boolean append)
    {
        this.file = file;
        this.append = append;
    }

    public File getFile()
    {
        return this.file;
    }

    public boolean isAppend()
    {
        return append;
    }

    public LogCycler getCycler()
    {
        return cycler;
    }

    public void setCycler(LogCycler cycler)
    {
        this.cycler = cycler;
    }

    private BufferedWriter open()
    {
        LogCycler cycler = this.getCycler();
        if (cycler != null)
        {
            this.file = cycler.cycle(this.file, closeCallBack);
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
            this.format.writeEntry(sb, poll);
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

    public void publish(LogEntry entry)
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

    public void setThreadFactory(ThreadFactory threadFactory)
    {
        this.threadFactory = threadFactory;
    }
}
