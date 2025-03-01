/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

abstract class AbsLoggerWrapper<LoggerType: Logger>: Logger() {

    abstract val logger: LoggerType

    override val filter: ((level: LogLevel, tag: String?) -> Boolean)?
        get() = logger.filter

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        logger.callDoLog(level, tag, message)
    }
}
