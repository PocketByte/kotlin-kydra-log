/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

actual fun HydraLog.initDefault(level: LogLevel?, tags: Set<String?>?) {
    if (level == null && tags?.isNotEmpty() != true)
        init(JsLogger())
    else
        init(FilteredLogger(JsLogger(), level, tags))
}