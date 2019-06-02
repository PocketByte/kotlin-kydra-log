/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

actual object HydraLog: AbsLogger() {

    actual override val logger
        get() = getOrInitLogger()

    private var innerLogger: Logger? = null

    actual fun init(logger: Logger) {
        if (this.innerLogger != null)
            throw IllegalStateException("HydraLog already initialized")
        this.innerLogger = logger
    }

    private fun getOrInitLogger(): Logger {
        val logger = this.innerLogger
        if (logger == null) {
            HydraLog.initDefault()
            return this.innerLogger!!
        }
        return logger
    }
}