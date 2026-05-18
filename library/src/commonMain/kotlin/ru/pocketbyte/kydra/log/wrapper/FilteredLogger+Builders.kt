/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * Wraps this logger in a [FilteredLoggerWrapper] using the provided filter function.
 *
 * @param filter Returns `true` if a record with the given level and tag should be forwarded,
 * or `false` to suppress it.
 */
fun <T : Logger> T.filtered(
    filter: (level: LogLevel, tag: String?) -> Boolean
): FilteredLoggerWrapper<T> {
    return FilteredLoggerWrapper(this, filter)
}

/**
 * Wraps this logger in a [FilteredLoggerWrapper] using the provided minimum log level
 * and set of allowed tags.
 *
 * @param level Minimum log level to pass through. `null` to disable level filtering.
 * @param tags Set of tags to pass through. `null` to disable tag filtering.
 */
fun <T : Logger> T.filtered(
    level: LogLevel? = null,
    tags: Set<String?>? = null
): FilteredLoggerWrapper<T> {
    return FilteredLoggerWrapper(this, level, tags)
}

/**
 * Wraps this logger in a [FilteredLoggerWrapper] using the provided level and tag filter predicates.
 *
 * @param levelFilter Log level filter predicate. `null` to disable level filtering.
 * @param tagFilter Tag filter predicate. `null` to disable tag filtering.
 */
fun <T : Logger> T.filtered(
    levelFilter: ((LogLevel) -> Boolean)? = null,
    tagFilter: ((String?) -> Boolean)? = null
): FilteredLoggerWrapper<T> {
    return FilteredLoggerWrapper(this, levelFilter, tagFilter)
}
