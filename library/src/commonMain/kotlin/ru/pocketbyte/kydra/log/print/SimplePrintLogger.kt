package ru.pocketbyte.kydra.log.print

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

open class SimplePrintLogger(
    private val printer: Printer,
    private val logMessageFormatter: LogMessageFormatter
) : Logger() {

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        printer.print(logMessageFormatter(level, tag, message))
    }
}
