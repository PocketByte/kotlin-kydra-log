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
class LoggerTagTransform<LoggerType: Logger>(
    override val logger: LoggerType,
    private val tagTransform: (tag: String?) -> String?
) : AbsLoggerWrapper<LoggerType>() {

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        super.doLog(level, tagTransform(tag), message)
    }
}
