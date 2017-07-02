package org.cubeengine.logscribe.target;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.cubeengine.logscribe.LogEntry;
import org.cubeengine.logscribe.LogTarget;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

public class EmailTarget extends LogTarget
{
    private final Function<LogEntry, Email> mapper;

    public EmailTarget(Function<LogEntry, Email> mapper)
    {
        this.mapper = mapper;
    }

    // TODO implement me
    @Override
    protected void publish(LogEntry entry)
    {
        Email mail = this.mapper.apply(entry);
        CompletableFuture.runAsync(() -> {
            try
            {
                mail.send();
            }
            catch (EmailException e)
            {
                throw new LogTargetException(e);
            }
        });
    }

    @Override
    protected void onShutdown()
    {
    }
}
