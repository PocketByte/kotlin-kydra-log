package ru.pocketbyte.kydra.log.print

import ru.pocketbyte.kydra.log.LogLevel

interface LogMessageFormatter {

    operator fun invoke(level: LogLevel, tag: String?, message: Any): String
}
