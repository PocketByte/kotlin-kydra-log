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

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class, UnsafeNumber::class)
class NativeLogMessageFormatter : AbsLogMessageFormatter() {

    override fun getTimeStamp(): String {
        return memScoped {
            val timeVar = alloc<time_tVar>()
            time(timeVar.ptr)
            ctime(timeVar.ptr)?.toKString()?.replace("[\n|\r]".toRegex(), "") ?: ""
        }
    }

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
