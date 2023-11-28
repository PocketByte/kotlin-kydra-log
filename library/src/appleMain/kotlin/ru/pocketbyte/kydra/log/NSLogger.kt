/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import platform.Foundation.*

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
@Deprecated("Use AppleLogger instead")
open class NSLogger: PrintLogger() {

    override fun printLog(message: String) {
        NSLog(message)
    }

    override fun stackTrace(exception: Throwable): String {
        return exception.stackTraceToString()
    }
}
