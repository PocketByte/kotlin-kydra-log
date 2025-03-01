package ru.pocketbyte.kydra.log

interface LogMessageFormatter {

    operator fun invoke(level: LogLevel, tag: String?, message: Any): String
}
