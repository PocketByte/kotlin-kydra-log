package ru.pocketbyte.hydra.log

fun HydraLog.initDefault(level: LogLevel? = null, tags: Set<String?>? = null) {
    init(IosLogger(level, tags))
}