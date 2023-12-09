/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Logger wrapper that overrides log tags.
 *
 * @param logger Logger to wrap
 * @param tagTransform Tag transformation logic
 */
class LoggerTagTransform(
    override val logger: Logger,
    private val tagTransform: (tag: String?) -> String?
) : AbsLoggerWrapper() {

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        super.doLog(level, tagTransform(tag), message)
    }
}

/**
 * Wraps Logger with Logger that transforms tags by given logic.
 *
 * @param tagTransform Tag transformation logic
 */
fun Logger.withTagTransform(tagTransform: (tag: String?) -> String?): Logger {
    return LoggerTagTransform(this, tagTransform)
}

/**
 * Wraps Logger with Logger that uses defaultTag if provided tag is null.
 *
 * @param defaultTag Tag that should be used if provided tag is null.
 */
fun Logger.withTag(defaultTag: String): Logger {
    return LoggerTagTransform(this) { it ?: defaultTag }
}
