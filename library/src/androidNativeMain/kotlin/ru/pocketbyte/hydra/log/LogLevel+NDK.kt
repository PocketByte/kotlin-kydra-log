package ru.pocketbyte.hydra.log

import platform.android.*

val LogLevel.native: android_LogPriority
    get() = when (this) {
        LogLevel.DEBUG -> ANDROID_LOG_DEBUG
        LogLevel.INFO -> ANDROID_LOG_INFO
        LogLevel.WARNING -> ANDROID_LOG_WARN
        LogLevel.ERROR -> ANDROID_LOG_ERROR
    }