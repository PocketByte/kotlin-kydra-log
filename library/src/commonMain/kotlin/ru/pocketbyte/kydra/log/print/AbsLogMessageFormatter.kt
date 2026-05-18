package ru.pocketbyte.kydra.log.print

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.ThrowableWithMessage

/**
 * A base implementation of [LogMessageFormatter] that assembles log strings
 * in the format: ``timestamp`: LEVEL/tag: message`.
 *
 * Subclasses must implement [getTimeStamp] and [appendThrowable] for [Throwable].
 * The individual append methods are `open` and can be overridden to customize
 * any part of the output format.
 */
abstract class AbsLogMessageFormatter : LogMessageFormatter {

    /** Returns the current timestamp string. If blank, no timestamp is prepended to the output. */
    protected abstract fun getTimeStamp(): String

    /**
     * Appends a string representation of [throwable] (e.g. stack trace) to this builder.
     * @return This builder, for chaining
     */
    protected abstract fun StringBuilder.appendThrowable(throwable: Throwable): StringBuilder

    /**
     * Formats a log record into a string of the form ``timestamp`: LEVEL/tag: message`.
     * @param level Log level of the record
     * @param tag Tag of the log record. Nullable
     * @param message Message of the log record
     * @return The formatted log string
     */
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

    /**
     * Appends the timestamp from [getTimeStamp] wrapped in brackets (e.g. `[12:00:00]: `).
     * Does nothing if the timestamp is blank.
     * @return This builder, for chaining
     */
    protected open fun StringBuilder.appendTimeStamp(): StringBuilder {
        val timestamp = getTimeStamp()
        if (timestamp.isNotBlank()) {
            append("[")
            append(timestamp)
            append("]: ")
        }
        return this
    }

    /**
     * Appends [message] to this builder, dispatching to the appropriate typed overload
     * based on the runtime type of [message].
     * @return This builder, for chaining
     */
    protected open fun StringBuilder.appendMessage(message: Any): StringBuilder {
        return when (message) {
            is ThrowableWithMessage -> appendThrowable(message)
            is Throwable -> appendThrowable(message)
            else -> append(message.toString())
        }
    }

    /**
     * Appends the text message of [throwableWithMessage] followed by its throwable.
     * @return This builder, for chaining
     */
    protected open fun StringBuilder.appendThrowable(
        throwableWithMessage: ThrowableWithMessage
    ): StringBuilder {
        append(throwableWithMessage.message)
        append("\n")
        return appendThrowable(throwableWithMessage.throwable)
    }

    /**
     * Appends a single-character log level code: `D`, `I`, `W`, or `E`.
     * @return This builder, for chaining
     */
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

    /**
     * Appends [tag] to this builder. Does nothing if [tag] is null or empty.
     * @return This builder, for chaining
     */
    protected open fun StringBuilder.appendTag(tag: String?): StringBuilder {
        if (tag?.isNotEmpty() == true) {
            append(tag)
        }
        return this
    }
}
