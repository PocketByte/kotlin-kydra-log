/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Global logger instance.
 * You should initialize it via [init] before usage, otherwise default logger will be used.
 */
object KydraLog: InitializableLogger() {

    /**
     * Init KydraLog instance with provided Logger
     * @param logger Logger that should be user for logging
     */
    override fun init(logger: Logger) {
        super.init(logger)
    }

    override fun defaultLogger(): Logger = DefaultLogger

}