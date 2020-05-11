/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
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
open class FilteredLogger(
        logger: Logger,
        val filter: (level: LogLevel, tag: String?) -> Boolean
) : LoggerWrapper(logger) {

    /**
     * Creates filtered logger depends on provided log level and set of tags.
     * @param logger The Logger that should be filtered.
     * @param level Minimum log level that can be passed.
     * Null if filter by LogLevel shouldn't be used.
     * @param tags Set of tags that can be passed.
     * Null if filter by Tag shouldn't be used.
     */
    constructor(logger: Logger, level: LogLevel? = null, tags: Set<String?>? = null) :
            this(logger, { pLevel: LogLevel, pTag: String? -> Boolean
                (level == null || pLevel.priority >= level.priority)
                        && (tags == null || tags.contains(pTag))
            })

    override fun log(level: LogLevel, tag: String?, message: String) {
        if (filter(level, tag)) {
            super.log(level, tag, message)
        }
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        if (filter(level, tag)) {
            super.log(level, tag, exception)
        }
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        if (filter(level, tag)) {
            super.log(level, tag, function)
        }
    }
}