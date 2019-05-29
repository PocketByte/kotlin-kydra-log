package ru.pocketbyte.hydra.log

/**
 * Initialize HydraLog with default Logger and provided filters.
 * @param level Minimum log level that can be passed.
 * Null if filter by LogLevel shouldn't be used.
 * @param tags Set of tags that can be passed.
 * Null if filter by Tag shouldn't be used.
 */
expect fun HydraLog.initDefault(level: LogLevel? = null, tags: Set<String?>? = null)