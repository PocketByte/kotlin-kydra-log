/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import platform.android.*
import kotlin.experimental.ExperimentalNativeApi

/**
 * Kotlin/Native implementation of [Logger] for Android NDK.
 * Writes log records via `__android_log_print` from the Android logging library.
 */
open class AndroidNativeLogger: AbsLogger() {

    override fun doLog(level: LogLevel, tag: String?, string: String) {
        __android_log_print(level.native.toInt(), tag, string)
    }

    override fun doLog(level: LogLevel, tag: String?, exception: Throwable) {
        doLog(level, tag, exception.stackTraceToString())
    }

    private val LogLevel.native: android_LogPriority
        get() = when (this) {
            LogLevel.DEBUG -> ANDROID_LOG_DEBUG
            LogLevel.INFO -> ANDROID_LOG_INFO
            LogLevel.WARNING -> ANDROID_LOG_WARN
            LogLevel.ERROR -> ANDROID_LOG_ERROR
        }
}
