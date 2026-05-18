package ru.pocketbyte.kydra.log.print

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKString
import platform.posix.ctime
import platform.posix.time
import platform.posix.time_tVar
import kotlin.experimental.ExperimentalNativeApi

/**
 * Kotlin/Native implementation of [AbsLogMessageFormatter].
 * Formats timestamps using the POSIX `ctime` function via C-interop and appends throwables
 * with their qualified class name, message, and full stack trace.
 */
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class, UnsafeNumber::class)
class NativeLogMessageFormatter : AbsLogMessageFormatter() {

    /**
     * Returns the current local time as a string provided by the POSIX `ctime` function,
     * with newline characters stripped.
     */
    override fun getTimeStamp(): String {
        return memScoped {
            val timeVar = alloc<time_tVar>()
            time(timeVar.ptr)
            ctime(timeVar.ptr)?.toKString()?.replace("[\n|\r]".toRegex(), "") ?: ""
        }
    }

    /**
     * Appends the qualified class name of [throwable], its message (if present),
     * and each stack trace element on a separate line.
     * @return This builder, for chaining
     */
    override fun StringBuilder.appendThrowable(throwable: Throwable): StringBuilder {
        append(throwable::class.qualifiedName ?: "unknown")
        throwable.message?.let {
            append(": ")
            append(it)
        }
        throwable.getStackTrace().forEach {
            append("\n")
            append(it)
        }
        return this
    }
}
