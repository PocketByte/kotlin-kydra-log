/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import java.util.concurrent.atomic.AtomicReference

actual abstract class InitializableLogger: AbsLoggerWrapper() {

    actual override val logger: Logger
        get() = loggerRef.get() ?: defaultLogger()

    actual val isInitialized: Boolean
        get() = loggerRef.get() != null

    private var loggerRef: AtomicReference<Logger?> = AtomicReference(null)

    protected actual abstract fun defaultLogger(): Logger

    actual open fun init(logger: Logger) {
        if (!this.loggerRef.compareAndSet(null, logger)) {
            throw IllegalStateException("Logger already initialized")
        }
    }
}
