/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

actual object HydraLog: Logger {

    private var logger: Logger? = null

    actual fun init(logger: Logger) {
        if (this.logger != null)
            throw IllegalStateException("HydraLog already initialized")
        this.logger = logger
    }

    actual override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        this.getOrInitLogger().log(level, tag, message, *arguments)
    }

    actual override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        this.getOrInitLogger().log(level, tag, exception)
    }

    actual override fun log(level: LogLevel, tag: String?, function: () -> String) {
        this.getOrInitLogger().log(level, tag, function)
    }

    private fun getOrInitLogger(): Logger {
        val logger = this.logger
        if (logger == null) {
            HydraLog.initDefaultJs()
            return getOrInitLogger()
        }
        return logger
    }
}