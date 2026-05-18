/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Severity level of a log record.
 *
 * Levels are ordered by increasing severity: [DEBUG] < [INFO] < [WARNING] < [ERROR].
 * The [priority] value reflects this order and can be used to implement threshold filtering.
 */
enum class LogLevel(
    /** Numeric severity value. Higher values indicate greater severity. */
    val priority: Int
) {

    /**
     * Verbose information useful during development and debugging.
     * Should not be enabled in production builds.
     */
    DEBUG(1),

    /**
     * General informational messages about normal application flow.
     */
    INFO(2),

    /**
     * Potentially harmful situations that do not prevent the application from functioning.
     */
    WARNING(3),

    /**
     * Errors that indicate a failure in the current operation or an unexpected condition.
     */
    ERROR(4)
}
