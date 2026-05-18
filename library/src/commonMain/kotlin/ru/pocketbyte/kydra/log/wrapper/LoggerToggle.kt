/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * A logger wrapper that can be dynamically enabled or disabled.
 * When disabled, all log records are suppressed without being forwarded to the wrapped logger.
 */
class LoggerToggle<LoggerType: Logger>(
    /** The logger to which records are forwarded when this toggle is enabled. */
    override val logger: LoggerType
) : AbsLoggerWrapper<LoggerType>() {

    /**
     * Controls whether log records are forwarded to the wrapped logger.
     * Set to `false` to suppress all records; `true` to resume logging.
     */
    var enabled: Boolean = true

    /** Returns `false` when [enabled] is `false`, suppressing all log records. */
    override val filter = { level: LogLevel, tag: String? ->
        enabled && logger.filter?.invoke(level, tag) != false
    }
}
