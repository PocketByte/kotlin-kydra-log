package ru.pocketbyte.kydra.log

import java.text.SimpleDateFormat
import java.util.*

class JvmLogMessageFormatter : LogMessageFormatter {

    private val dateFormat = SimpleDateFormat("HH:mm:ss")
    override fun invoke(level: LogLevel, tag: String?, message: Any): String {
        val builder = StringBuilder()

        val timestamp = dateFormat.format(Date())
        builder.append("[$timestamp]: ")

        builder.append(
            when (level) {
                LogLevel.INFO -> "I"
                LogLevel.DEBUG -> "D"
                LogLevel.WARNING -> "W"
                LogLevel.ERROR -> "E"
            }
        )
        builder.append("/")

        if (tag?.isNotEmpty() == true) {
            builder.append(tag)
        }

        builder.append(": ")
        if (message is Throwable) {
            mapThrowable(builder, message)
        } else {
            builder.append(message.toString())
        }
        return builder.toString()
    }

    private fun mapThrowable(builder: StringBuilder, throwable: Throwable) {
        builder.append(throwable::class.qualifiedName ?: "unknown")
        throwable.message?.let {
            builder.append(": ")
            builder.append(it)
        }
        throwable.stackTrace.forEach {
            builder.append("\n")
            builder.append(it)
        }
    }
}
