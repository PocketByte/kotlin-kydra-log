/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Logger container.
 * You should initialize it via [init] before usage, otherwise default logger will be used.
 */
expect abstract class InitializableLogger() : AbsLoggerWrapper {

    override val logger: Logger

    val isInitialized: Boolean

    /**
     * Init Logger with provided Logger
     * @param logger Logger that should be user for logging
     */
    open fun init(logger: Logger)

    protected abstract fun defaultLogger(): Logger

}