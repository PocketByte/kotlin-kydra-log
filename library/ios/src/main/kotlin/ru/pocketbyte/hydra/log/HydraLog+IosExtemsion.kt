package ru.pocketbyte.hydra.log

fun HydraLog.initDefaultIos(level: LogLevel? = null, tags: Set<String?>? = null) {
    if (level == null && tags?.isNotEmpty() != true)
        init(IosLogger())
    else
        init(FilteredLogger(IosLogger(), level, tags))
}