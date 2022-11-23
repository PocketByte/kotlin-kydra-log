/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * The Level of Logging.
 *
 * @property priority Priority of the Level.
 */
enum class LogLevel(val priority: Int) {

    /**
     * Debug Log Level. Should be shown only for debugging.
     */
    DEBUG(1),

    /**
     * Information Log Level.
     */
    INFO(2),

    /**
     * Warning Log Level. Should be used to log some warnings.
     */
    WARNING(3),

    /**
     * Error Log Level. Should be used to log some errors.
     */
    ERROR(4)
}