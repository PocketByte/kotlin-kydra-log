/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Returns Logger wrapped with FilteredLogger
 *
 * @property filter Filter function that defines which logs should be filtered
 */
fun Logger.filtered(filter: (level: LogLevel, tag: String?) -> Boolean): Logger {
    return FilteredLoggerWrapper(this, filter)
}


/**
 * Returns Logger wrapped with FilteredLogger
 *
 * @param level Minimum log level that can be passed.
 * Null if filter by LogLevel shouldn't be used.
 * @param tags Set of tags that can be passed.
 * Null if filter by Tag shouldn't be used.
 */
fun Logger.filtered(level: LogLevel? = null, tags: Set<String?>? = null): Logger {
    if ((level == null || level == LogLevel.DEBUG) && tags.isNullOrEmpty())
        return this

    return FilteredLoggerWrapper(this, level, tags)
}

/**
 * Returns Logger wrapped with FilteredLogger
 *
 * @param levelFiler Log level filter rule.
 * Null if filter by LogLevel shouldn't be used.
 * @param tagFilter Tag filter rule.
 * Null if filter by Tag shouldn't be used.
 */
fun Logger.filtered(
    levelFiler: ((LogLevel) -> Boolean)? = null,
    tagFilter: ((String?) -> Boolean)? = null
): Logger {
    if (levelFiler == null && tagFilter == null)
        return this

    return FilteredLoggerWrapper(this, levelFiler, tagFilter)
}
