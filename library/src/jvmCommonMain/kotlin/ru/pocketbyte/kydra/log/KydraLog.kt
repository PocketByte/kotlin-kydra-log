/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

actual object KydraLog: AbsLogger() {

    actual override val logger
        get() = getOrInitLogger()

    private var innerLogger: Logger? = null

    actual fun init(logger: Logger) {
        synchronized(this) {
            if (this.innerLogger != null)
                throw IllegalStateException("KydraLog already initialized")
            this.innerLogger = logger
        }
    }

    private fun getOrInitLogger(): Logger {
        val logger = this.innerLogger
        if (logger == null) {
            KydraLog.initDefault()
            return this.innerLogger!!
        }
        return logger
    }
}