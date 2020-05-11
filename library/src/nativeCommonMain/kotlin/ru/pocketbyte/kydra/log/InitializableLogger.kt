/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

actual abstract class InitializableLogger: AbsLogger() {

    actual override val logger
        get() = getOrInitLogger()

    private var loggerRef: AtomicReference<Logger?> = AtomicReference(null)

    protected actual abstract fun onNeedToBeInitialized()

    actual open fun init(logger: Logger) {
        if (!this.loggerRef.compareAndSet(null, logger.freeze()))
            throw IllegalStateException("Logger already initialized")
    }

    private fun getOrInitLogger(): Logger {
        val logger = this.loggerRef.value
        if (logger == null) {
            onNeedToBeInitialized()
            return this.loggerRef.value
                ?: throw IllegalStateException("Logger need to be initialized before usage")
        }
        return logger
    }
}