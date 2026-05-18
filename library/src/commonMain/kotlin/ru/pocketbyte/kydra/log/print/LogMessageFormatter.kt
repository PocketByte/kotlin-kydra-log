package ru.pocketbyte.kydra.log.print

import ru.pocketbyte.kydra.log.LogLevel

/**
 * Converts a log record into a formatted string ready to be written by a [Printer].
 * Instances can be called directly as functions due to the [invoke] operator.
 */
interface LogMessageFormatter {

    /**
     * Formats a log record into a string.
     * @param level Log level of the record
     * @param tag Tag of the log record. Nullable
     * @param message Message of the log record
     * @return The formatted log string
     */
    operator fun invoke(level: LogLevel, tag: String?, message: Any): String
}
