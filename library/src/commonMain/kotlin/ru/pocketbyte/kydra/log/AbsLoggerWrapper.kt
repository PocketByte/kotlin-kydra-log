/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

abstract class AbsLoggerWrapper: Logger {

    abstract val logger: Logger

    override fun log(level: LogLevel, tag: String?, message: Any) {
        this.logger.log(level, tag, message)
    }
}