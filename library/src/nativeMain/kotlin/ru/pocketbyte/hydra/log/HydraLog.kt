/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

actual object HydraLog: AbsLogger() {

    actual override val logger
            get() = getOrInitLogger()

    private var loggerRef: AtomicReference<Logger?> = AtomicReference(null)

    actual fun init(logger: Logger) {
        if (!this.loggerRef.compareAndSet(null, logger.freeze()))
            throw IllegalStateException("HydraLog already initialized")
    }

    private fun getOrInitLogger(): Logger {
        val logger = this.loggerRef.value
        if (logger == null) {
            HydraLog.initDefault()
            return this.loggerRef.value!!
        }
        return logger
    }
}
