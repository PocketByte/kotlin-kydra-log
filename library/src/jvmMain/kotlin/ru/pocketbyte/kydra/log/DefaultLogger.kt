package ru.pocketbyte.kydra.log

actual object DefaultLogger {
    actual fun build(level: LogLevel?, tags: Set<String?>?): Logger {
        return if (level == null && tags?.isNotEmpty() != true)
            PrintLogger()
        else
            FilteredLogger(PrintLogger(), level, tags)
    }
}