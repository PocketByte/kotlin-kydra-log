/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.concurrent.AtomicReference

actual abstract class InitializableLogger<LoggerType: Logger>
    : AbsLoggerWrapper<LoggerType>() {

    actual override val logger: LoggerType
        get() = loggerRef.value ?: defaultLogger

    actual val isInitialized: Boolean
        get() = loggerRef.value != null

    protected actual abstract val defaultLogger: LoggerType

    private var loggerRef: AtomicReference<LoggerType?> = AtomicReference(null)

    actual open fun init(logger: LoggerType) {
        if (!this.loggerRef.compareAndSet(null, logger)) {
            throw IllegalStateException("Logger already initialized")
        }
    }
}
