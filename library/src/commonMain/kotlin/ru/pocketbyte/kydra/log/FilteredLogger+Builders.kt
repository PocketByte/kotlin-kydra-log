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
    return FilteredLogger(this, filter)
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

    return FilteredLogger(this, level, tags)
}