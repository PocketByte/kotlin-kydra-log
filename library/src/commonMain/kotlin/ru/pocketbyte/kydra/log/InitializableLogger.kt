/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Logger container.
 * You should initialize it via [init] before usage, otherwise default logger will be used.
 */
expect abstract class InitializableLogger<LoggerType: Logger>()
    : AbsLoggerWrapper<LoggerType> {

    override val logger: LoggerType

    val isInitialized: Boolean

    protected abstract val defaultLogger: LoggerType

    /**
     * Init Logger with provided Logger
     * @param logger Logger that should be user for logging
     */
    open fun init(logger: LoggerType)

}