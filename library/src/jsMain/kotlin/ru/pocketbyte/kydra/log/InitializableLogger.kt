/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

actual abstract class InitializableLogger: AbsLoggerWrapper() {

    actual override val logger: Logger
        get() = innerLogger ?: defaultLogger()

    actual val isInitialized: Boolean
        get() = innerLogger != null

    private var innerLogger: Logger? = null

    protected actual abstract fun defaultLogger(): Logger

    actual open fun init(logger: Logger) {
        if (innerLogger != null)
            throw IllegalStateException("Logger already initialized")
        innerLogger = logger
    }
}
