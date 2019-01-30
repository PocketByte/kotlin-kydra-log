/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

/**
 * Global logger instance.
 * You should initialize it via [init] before usage.
 */
expect object HydraLog: Logger {

    /**
     * Init HydraLog instance with provided Logger
     * @param logger Logger that should be user for logging
     */
    fun init(logger: Logger)

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any)

    override fun log(level: LogLevel, tag: String?, exception: Throwable)

    override fun log(level: LogLevel, tag: String?, function: () -> String)

}