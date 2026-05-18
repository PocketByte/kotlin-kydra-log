/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * A logger wrapper that forwards log records to the wrapped logger
 * only if they satisfy the given filter.
 *
 * @property logger The logger to which passing records are forwarded.
 * @property filter Returns `true` if a record with the given level and tag should be forwarded,
 * or `false` to suppress it. Also incorporates the wrapped logger's own filter.
 *
 * @constructor Creates a filtered logger using the provided filter function.
 */
open class FilteredLoggerWrapper<LoggerType: Logger>(
    override val logger: LoggerType,
    filter: (level: LogLevel, tag: String?) -> Boolean
) : AbsLoggerWrapper<LoggerType>() {

    override val filter: (level: LogLevel, tag: String?) -> Boolean = { level, tag ->
        logger.filter?.invoke(level, tag) != false && filter.invoke(level, tag)
    }

    /**
     * Creates a filtered logger using the provided minimum log level and set of allowed tags.
     * @param logger The logger to which passing records are forwarded.
     * @param level Minimum log level to pass through. `null` to disable level filtering.
     * @param tags Set of tags to pass through. `null` to disable tag filtering.
     */
    constructor(
        logger: LoggerType,
        level: LogLevel? = null,
        tags: Set<String?>? = null
    ) : this(
        logger,
        levelFilter = level
            ?.let { { level: LogLevel ->  level.priority >= it.priority} },
        tagFilter = tags
            ?.let { { tag: String? -> it.contains(tag) } }
    )

    /**
     * Creates a filtered logger using the provided level and tag filter predicates.
     * @param logger The logger to which passing records are forwarded.
     * @param levelFilter Log level filter predicate. `null` to disable level filtering.
     * @param tagFilter Tag filter predicate. `null` to disable tag filtering.
     */
    constructor(
        logger: LoggerType,
        levelFilter: ((LogLevel) -> Boolean)? = null,
        tagFilter: ((String?) -> Boolean)? = null
    ) : this(
        logger,
        { level: LogLevel, tag: String? ->
            (levelFilter == null || levelFilter(level))
                    && (tagFilter == null || tagFilter(tag))
        }
    )
}
