/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

actual fun HydraLog.initDefault(level: LogLevel?, tags: Set<String?>?) {
    HydraLog.initDefaultJava(level, tags)
}

/**
 * Initialize HydraLog with default Java Logger and provided filters.
 * @param level Minimum log level that can be passed.
 * Null if filter by LogLevel shouldn't be used.
 * @param tags Set of tags that can be passed.
 * Null if filter by Tag shouldn't be used.
 */
fun HydraLog.initDefaultJava(level: LogLevel? = null, tags: Set<String?>? = null) {
    if (level == null && tags?.isNotEmpty() != true)
        init(PrintLogger())
    else
        init(FilteredLogger(PrintLogger(), level, tags))
}