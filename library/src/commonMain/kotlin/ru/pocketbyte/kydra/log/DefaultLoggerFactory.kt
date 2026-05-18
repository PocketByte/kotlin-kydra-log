/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Factory that creates the default [Logger] for the current platform.
 * The returned logger uses the most appropriate logging mechanism available
 * (e.g. Logcat on Android, console on JS, standard output on JVM and Native).
 */
expect object DefaultLoggerFactory {
    @Deprecated(
        message = "Use create() instead.",
        replaceWith = ReplaceWith("create()"),
        level = DeprecationLevel.ERROR
    )
    fun build(): Logger

    /** Creates and returns the default logger for the current platform. */
    fun create(): Logger
}