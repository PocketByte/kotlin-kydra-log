/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * The Logger implementation that wraps another logger
 * and only passes events that satisfy the filter.
 *
 * @property logger The Logger that should be filtered.
 * @property filter Filter function that defines which logs should be filtered
 *
 * @constructor Creates filtered logger depends on provided filter function.
 */
open class FilteredLoggerWrapper(
    override val logger: Logger,
    filter: (level: LogLevel, tag: String?) -> Boolean
) : AbsLoggerWrapper() {

    override val filter: (level: LogLevel, tag: String?) -> Boolean = { level, tag ->
        logger.filter?.invoke(level, tag) != false && filter.invoke(level, tag)
    }

    /**
     * Creates filtered logger depends on provided log level and set of tags.
     * @param logger The Logger that should be filtered.
     * @param level Minimum log level that can be passed.
     * Null if filter by LogLevel shouldn't be used.
     * @param tags Set of tags that can be passed.
     * Null if filter by Tag shouldn't be used.
     */
    constructor(
        logger: Logger,
        level: LogLevel? = null,
        tags: Set<String?>? = null
    ) : this(
        logger,
        { pLevel: LogLevel, pTag: String? -> Boolean
            (level == null || pLevel.priority >= level.priority)
                    && (tags == null || tags.contains(pTag))
        }
    )
}
