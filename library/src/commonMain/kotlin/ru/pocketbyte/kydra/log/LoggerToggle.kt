/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Logger wrapper implementation that allows to toggle logger enability.
 */
class LoggerToggle(
    override val logger: Logger
) : AbsLoggerWrapper() {

    /**
     * Logger enability. If false, all log messages will be skipped.
     */
    var enabled: Boolean = true

    override val filter = { level: LogLevel, tag: String? ->
        enabled && logger.filter?.invoke(level, tag) != false
    }
}
