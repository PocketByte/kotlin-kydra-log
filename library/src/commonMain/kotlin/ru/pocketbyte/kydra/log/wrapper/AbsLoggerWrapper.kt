/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * Base class for logger wrappers. Delegates [filter] and [doLog] to the wrapped [logger],
 * so subclasses only need to override the behavior they want to change.
 *
 * Note: [doLog] bypasses the wrapped logger's own filter by calling [Logger.callDoLog] directly.
 * Filtering is the responsibility of the wrapper, not the wrapped logger.
 */
abstract class AbsLoggerWrapper<LoggerType: Logger>: Logger() {

    /** The logger this wrapper delegates to. */
    protected abstract val logger: LoggerType

    /** Delegates to the wrapped [logger]'s filter. Override to apply a different filter. */
    override val filter: ((level: LogLevel, tag: String?) -> Boolean)?
        get() = logger.filter

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        logger.callDoLog(level, tag, message)
    }
}
