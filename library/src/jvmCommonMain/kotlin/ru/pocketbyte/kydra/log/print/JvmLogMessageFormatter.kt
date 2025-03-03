package ru.pocketbyte.kydra.log.print

import java.text.SimpleDateFormat
import java.util.*

class JvmLogMessageFormatter : AbsLogMessageFormatter() {

    private val dateFormat = SimpleDateFormat("HH:mm:ss")

    override fun getTimeStamp(): String {
        return dateFormat.format(Date())
    }

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
