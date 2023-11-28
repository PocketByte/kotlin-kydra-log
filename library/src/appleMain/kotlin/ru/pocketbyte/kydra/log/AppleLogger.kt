/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import platform.darwin.OS_LOG_DEFAULT
import platform.darwin.OS_LOG_TYPE_DEBUG
import platform.darwin.OS_LOG_TYPE_DEFAULT
import platform.darwin.OS_LOG_TYPE_ERROR
import platform.darwin.OS_LOG_TYPE_INFO
import platform.darwin.__dso_handle
import platform.darwin._os_log_internal
import platform.darwin.os_log_t

@OptIn(ExperimentalForeignApi::class)
open class AppleLogger(
    private val logT: os_log_t = OS_LOG_DEFAULT
) : AbsLogger() {
    override fun doLog(level: LogLevel, tag: String?, string: String) {
        _os_log_internal(
            __dso_handle.ptr, logT,
            level.toOSLogLevel(),
            if (tag != null) "$tag: %s" else "%s",
            string
        )
    }

    override fun doLog(level: LogLevel, tag: String?, exception: Throwable) {
        doLog(level, tag, exception.stackTraceToString())
    }

    private fun LogLevel.toOSLogLevel(): UByte = when (this) {
        LogLevel.DEBUG -> OS_LOG_TYPE_DEBUG
        LogLevel.INFO -> OS_LOG_TYPE_INFO
        LogLevel.WARNING -> OS_LOG_TYPE_DEFAULT
        LogLevel.ERROR -> OS_LOG_TYPE_ERROR
    }
}
