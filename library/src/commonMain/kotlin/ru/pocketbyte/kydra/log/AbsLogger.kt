/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

abstract class AbsLogger: Logger {

    abstract val logger: Logger

    override fun log(level: LogLevel, tag: String?, message: String) {
        this.logger.log(level, tag, message)
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        this.logger.log(level, tag, exception)
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        this.logger.log(level, tag, function)
    }
}