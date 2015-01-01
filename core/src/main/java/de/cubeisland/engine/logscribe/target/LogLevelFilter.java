package de.cubeisland.engine.logscribe.target;

import java.util.HashMap;
import java.util.Map;

import de.cubeisland.engine.logscribe.LogEntry;
import de.cubeisland.engine.logscribe.LogLevel;
import de.cubeisland.engine.logscribe.filter.LogFilter;

/**
 * This filter maps the default log levels to custom levels.
 * This is useful for targets that have different priorities.
 */
public abstract class LogLevelFilter implements LogFilter
{
    private final Map<LogLevel, LogLevel> levelMap;

    public LogLevelFilter()
    {
        this.levelMap = new HashMap<LogLevel, LogLevel>();
        this.initLevels(this.levelMap);
    }

    /**
     * Initializes the map
     */
    protected abstract void initLevels(Map<LogLevel, LogLevel> levels);

    public boolean accept(LogEntry entry)
    {
        LogLevel level = this.levelMap.get(entry.getLevel());
        if (level == null)
        {
            return false;
        }
        entry.setLevel(level);
        return true;
    }
}
