/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * Logger wrapper that overrides log tags.
 *
 * @param logger Logger to wrap
 * @param tagTransform Tag transformation logic
 */
class LoggerTagTransform<LoggerType: Logger>(
    override val logger: LoggerType,
    private val tagTransform: (tag: String?) -> String?
) : AbsLoggerWrapper<LoggerType>() {

    /**
     * Delegates to the wrapped logger's filter, but applies [tagTransform] to the tag first.
     * This ensures that the filter sees the same tag that will be used in [doLog],
     * so filtering and logging are consistent.
     * Returns `null` if the wrapped logger has no filter.
     */
    override val filter: ((level: LogLevel, tag: String?) -> Boolean)?
        get() = if (logger.filter != null) innerFilter else null

    private val innerFilter: ((level: LogLevel, tag: String?) -> Boolean) = { level, tag ->
        logger.filter?.invoke(level, tagTransform(tag)) ?: false
    }

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        super.doLog(level, tagTransform(tag), message)
    }
}
