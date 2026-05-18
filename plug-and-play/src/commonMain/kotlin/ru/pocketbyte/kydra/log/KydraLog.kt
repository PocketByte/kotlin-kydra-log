/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import ru.pocketbyte.kydra.log.wrapper.InitializableLogger

/**
 * Global singleton logger for plug-and-play usage.
 * Before [init] is called, log records are forwarded to the default platform logger
 * created by [DefaultLoggerFactory].
 */
object KydraLog: InitializableLogger<Logger>() {

    override val defaultLogger: Logger by lazy {
        DefaultLoggerFactory.create()
    }

    /**
     * Sets the active logger for this global instance.
     * @param logger The logger to use for logging
     * @throws IllegalStateException if the logger has already been initialized
     */
    override fun init(logger: Logger) {
        super.init(logger)
    }
}