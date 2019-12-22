/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

actual fun KydraLog.initDefault(level: LogLevel?, tags: Set<String?>?) {
    if (level == null && tags?.isNotEmpty() != true)
        init(AndroidLogger())
    else
        init(FilteredLogger(AndroidLogger(), level, tags))
}