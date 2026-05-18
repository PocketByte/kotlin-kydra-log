/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * A container that pairs a [Throwable] with a descriptive text message.
 * Used as a log message type to convey both a human-readable description
 * and an exception in a single log call.
 *
 * The idiomatic way to create an instance is via the [withMessage] infix extension:
 * ```
 * exception withMessage "Failed to load config"
 * ```
 */
data class ThrowableWithMessage(
    /** Human-readable description of the event that caused the exception. */
    val message: String,
    /** The exception associated with the log record. */
    val throwable: Throwable
) {
    /**
     * Returns a string containing [message] followed by the full stack trace of [throwable],
     * separated by a newline.
     */
    override fun toString(): String = "$message\n${throwable.stackTraceToString()}"
}

/**
 * Creates a [ThrowableWithMessage] pairing this exception with the given [message].
 *
 * Usage:
 * ```
 * logger.error { exception withMessage "Failed to load config" }
 * ```
 */
infix fun Throwable.withMessage(message: String): ThrowableWithMessage = ThrowableWithMessage(message, this)
