/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

actual abstract class InitializableLogger: AbsLogger() {

    actual override val logger
    get() = getOrInitLogger()

    private var innerLogger: Logger? = null

    protected actual abstract fun onNeedToBeInitialized()

    actual open fun init(logger: Logger) {
        if (this.innerLogger != null)
            throw IllegalStateException("Logger already initialized")
        this.innerLogger = logger
    }

    private fun getOrInitLogger(): Logger {
        val logger = this.innerLogger
        if (logger == null) {
            onNeedToBeInitialized()
            return this.innerLogger
                ?: throw IllegalStateException("Logger need to be initialized before usage")
        }
        return logger
    }
}