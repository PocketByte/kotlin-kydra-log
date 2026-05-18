/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import android.util.Log
import ru.pocketbyte.kydra.log.wrapper.filtered

actual object DefaultLoggerFactory {
    actual fun build(level: LogLevel?, tags: Set<String?>?): Logger {
        return build().filtered(level, tags)
    }

    actual fun build(): Logger {
        return create()
    }

    actual fun create(): Logger {
        try {
            // Log.isLoggable throws in non-Android JVM environments
            // (e.g. unit tests without Robolectric).
            // Use it as a probe to detect the Android runtime.
            Log.isLoggable(null, Log.INFO)
        } catch (_: Exception) {
            return PrintLogger()
        }
        return AndroidLogger()
    }
}