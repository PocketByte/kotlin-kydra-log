/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

actual object DefaultLoggerFactory {
    actual fun build(level: LogLevel?, tags: Set<String?>?): Logger {
        return build().filtered(level, tags)
    }

    actual fun build(): Logger {
        return PrintLogger()
    }
}