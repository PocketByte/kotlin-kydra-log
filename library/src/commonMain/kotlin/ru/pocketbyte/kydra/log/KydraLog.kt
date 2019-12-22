/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Global logger instance.
 * You should initialize it via [init] before usage.
 */
expect object KydraLog: AbsLogger {

    override val logger: Logger

    /**
     * Init KydraLog instance with provided Logger
     * @param logger Logger that should be user for logging
     */
    fun init(logger: Logger)

}