package ru.pocketbyte.kydra.log.print

import java.text.SimpleDateFormat
import java.util.*

/**
 * JVM implementation of [AbsLogMessageFormatter].
 * Formats timestamps as `HH:mm:ss` and appends throwables with their qualified class name,
 * message, and full stack trace.
 */
class JvmLogMessageFormatter : AbsLogMessageFormatter() {

    private val dateFormat = SimpleDateFormat("HH:mm:ss")

    /** Returns the current time formatted as `HH:mm:ss`. */
    override fun getTimeStamp(): String {
        return dateFormat.format(Date())
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
        throwable.stackTrace.forEach {
            append("\n")
            append(it)
        }
        return this
    }
}
