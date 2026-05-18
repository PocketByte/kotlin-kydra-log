/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.DefaultLogger
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * Initialize InitializableLogger with default Logger and provided filters.
 * @param level Minimum log level that can be passed.
 * Null if filter by LogLevel shouldn't be used.
 * @param tags Set of tags that can be passed.
 * Null if filter by Tag shouldn't be used.
 */
fun InitializableLogger<Logger>.initDefault(level: LogLevel? = null, tags: Set<String?>? = null) {
    init(DefaultLogger.filtered(level, tags))
}

/**
 * Initialize InitializableLogger if it not initialized, otherwise ignore provided logger.
 * @param logger Logger that should be user for logging
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