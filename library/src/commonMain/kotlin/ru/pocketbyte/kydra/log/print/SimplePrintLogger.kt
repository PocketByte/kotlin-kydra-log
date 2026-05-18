package ru.pocketbyte.kydra.log.print

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * A [Logger] that formats each log record using a [LogMessageFormatter]
 * and writes the result via a [Printer].
 * @param printer The output destination
 * @param logMessageFormatter The formatter used to convert log records into strings
 */
open class SimplePrintLogger(
    private val printer: Printer,
    private val logMessageFormatter: LogMessageFormatter
) : Logger() {

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        printer.print(logMessageFormatter(level, tag, message))
    }
}
