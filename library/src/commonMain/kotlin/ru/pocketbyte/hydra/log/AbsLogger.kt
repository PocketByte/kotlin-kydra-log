/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

abstract class AbsLogger: Logger {

    abstract val logger: Logger

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        this.logger.log(level, tag, message, *arguments)
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        this.logger.log(level, tag, exception)
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        this.logger.log(level, tag, function)
    }
}