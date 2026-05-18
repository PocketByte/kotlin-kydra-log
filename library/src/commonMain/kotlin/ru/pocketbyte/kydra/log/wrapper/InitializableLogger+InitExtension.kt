/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.DefaultLogger
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * Initializes this logger with the default platform logger wrapped in a filter.
 * @param level Minimum log level to pass through. `null` to disable level filtering.
 * @param tags Set of tags to pass through. `null` to disable tag filtering.
 */
fun InitializableLogger<Logger>.initDefault(level: LogLevel? = null, tags: Set<String?>? = null) {
    init(DefaultLogger.filtered(level, tags))
}

/**
 * Initializes this logger with the provided [logger] if it has not been initialized yet.
 * If already initialized, the call is silently ignored.
 * @param logger The logger to use for logging
 */
fun <T : Logger> InitializableLogger<T>.initOrIgnore(logger: T) {
    if (!isInitialized) {
        try {
            init(logger)
        } catch (_: IllegalStateException) {
            // Already initialized by another thread — ignore
        }
    }
}
