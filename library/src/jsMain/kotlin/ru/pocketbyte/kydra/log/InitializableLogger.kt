/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

actual abstract class InitializableLogger<LoggerType: Logger>
    : AbsLoggerWrapper<LoggerType>() {

    actual override val logger: LoggerType
        get() = innerLogger ?: defaultLogger

    actual val isInitialized: Boolean
        get() = innerLogger != null

    protected actual abstract val defaultLogger: LoggerType

    private var innerLogger: LoggerType? = null

    actual open fun init(logger: LoggerType) {
        if (innerLogger != null)
            throw IllegalStateException("Logger already initialized")
        innerLogger = logger
    }
}
