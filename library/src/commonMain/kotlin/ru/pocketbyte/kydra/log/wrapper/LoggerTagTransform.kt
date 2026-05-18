/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * A logger wrapper that applies a transformation to the tag of every log record
 * before forwarding it to the wrapped logger.
 *
 * @param logger Logger to wrap
 * @param tagTransform Function that receives the original tag and returns the transformed tag.
 * May return `null` to clear the tag.
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

    /**
     * Applies [tagTransform] to [tag] and forwards the record to the wrapped logger.
     * @param level Log level
     * @param tag Original tag of the log record. Nullable
     * @param message Message to be written into log
     */
    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        super.doLog(level, tagTransform(tag), message)
    }
}
