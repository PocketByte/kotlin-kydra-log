package ru.pocketbyte.hydra.log

fun HydraLog.initDefaultJs(level: LogLevel? = null, tags: Set<String?>? = null) {
    if (level == null && tags?.isNotEmpty() != true)
        init(JsLogger())
    else
        init(FilteredLogger(JsLogger(), level, tags))
}