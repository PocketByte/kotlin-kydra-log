package ru.pocketbyte.kydra.log.print

import ru.pocketbyte.kydra.log.LogLevel

abstract class AbsLogMessageFormatter : LogMessageFormatter{

    protected abstract fun getTimeStamp(): String
    protected abstract fun StringBuilder.appendThrowable(throwable: Throwable): StringBuilder

    override fun invoke(level: LogLevel, tag: String?, message: Any): String {
        return StringBuilder()
            .appendTimeStamp()
            .appendLogLevel(level)
            .append("/")
            .appendTag(tag)
            .append(": ")
            .appendMessage(message)
            .toString()
    }

    protected open fun StringBuilder.appendTimeStamp(): StringBuilder {
        val timestamp = getTimeStamp()
        if (timestamp.isNotBlank()) {
            append("[")
            append(timestamp)
            append("]: ")
        }
        return this
    }

    protected open fun StringBuilder.appendMessage(message: Any): StringBuilder {
        return if (message is Throwable) {
            appendThrowable(message)
        } else {
            append(message.toString())
        }
    }

    protected open fun StringBuilder.appendLogLevel(level: LogLevel): StringBuilder {
        return append(
            when (level) {
                LogLevel.INFO -> "I"
                LogLevel.DEBUG -> "D"
                LogLevel.WARNING -> "W"
                LogLevel.ERROR -> "E"
            }
        )
    }

    protected open fun StringBuilder.appendTag(tag: String?): StringBuilder {
        if (tag?.isNotEmpty() == true) {
            append(tag)
        }
        return this
    }
}
