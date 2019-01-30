package ru.pocketbyte.hydra.log

fun HydraLog.initDefaultAndroid(level: LogLevel? = null, tags: Set<String?>? = null) {
    if (level == null && tags?.isNotEmpty() != true)
        init(AndroidLogger())
    else
        init(FilteredLogger(AndroidLogger(), level, tags))
}