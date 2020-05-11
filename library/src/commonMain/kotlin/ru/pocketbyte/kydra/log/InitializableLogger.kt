/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Logger container.
 * You should initialize it via [init] before usage.
 */
expect abstract class InitializableLogger() : AbsLogger {

    override val logger: Logger

    /**
     * Init Logger with provided Logger
     * @param logger Logger that should be user for logging
     */
    open fun init(logger: Logger)

    protected abstract fun onNeedToBeInitialized()

}