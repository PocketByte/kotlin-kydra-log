package ru.pocketbyte.kydra.log

expect object DefaultLogger {
    fun build(level: LogLevel? = null, tags: Set<String?>? = null): Logger
}