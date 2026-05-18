/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.Logger

/**
 * A logger wrapper that supports deferred initialization.
 * Before [init] is called, all log records are delegated to [defaultLogger].
 * Once initialized, all log records are delegated to the provided logger.
 *
 * Typical usage is to declare an instance as a global singleton and call [init]
 * during application startup.
 */
expect abstract class InitializableLogger<LoggerType: Logger>()
    : AbsLoggerWrapper<LoggerType> {

    /** The currently active logger. Returns [defaultLogger] until [init] is called. */
    override val logger: LoggerType

    /** Returns `true` if [init] has been called, `false` otherwise. */
    val isInitialized: Boolean

    /** The logger used before [init] is called. */
    protected abstract val defaultLogger: LoggerType

    /**
     * Sets the active logger. After this call, all log records are delegated to [logger].
     * @param logger The logger to use for logging
     * @throws IllegalStateException if the logger has already been initialized
     */
    open fun init(logger: LoggerType)

}