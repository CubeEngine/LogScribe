package de.cubeisland.engine.logscribe.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import javax.sql.DataSource;
import de.cubeisland.engine.logscribe.LogEntry;
import de.cubeisland.engine.logscribe.LogTarget;

public class DatabaseTarget extends LogTarget
{
    private final DataSource dataSource;
    private final String insertStatement;
    private final LogEntryBinder binder;

    public DatabaseTarget(DataSource dataSource, String insertStatement, LogEntryBinder binder)
    {
        this.dataSource = dataSource;
        this.insertStatement = insertStatement;
        this.binder = binder;
    }

    @Override
    protected void publish(LogEntry entry)
    {
        CompletableFuture.runAsync(() -> {
            Connection connection = null;
            PreparedStatement statement = null;
            try
            {
                connection = dataSource.getConnection();
                statement = connection.prepareStatement(this.insertStatement);
                this.binder.bind(statement, entry);
                statement.execute();
            }
            catch (SQLException e)
            {
                throw new DatabaseTargetException(e);
            }
            finally
            {
                if (statement != null)
                {
                    try
                    {
                        statement.close();
                    }
                    catch (SQLException ignored)
                    {}
                }
                if (connection != null)
                {
                    try
                    {
                        connection.close();
                    }
                    catch (SQLException ignored)
                    {}
                }
            }
        });
    }

    @Override
    protected void onShutdown()
    {
    }

    @FunctionalInterface
    public interface LogEntryBinder
    {
        void bind(PreparedStatement statement, LogEntry entry);
    }

    public static class DatabaseTargetException extends LogTargetException
    {
        DatabaseTargetException(Throwable cause)
        {
            super(cause);
        }
    }
}
