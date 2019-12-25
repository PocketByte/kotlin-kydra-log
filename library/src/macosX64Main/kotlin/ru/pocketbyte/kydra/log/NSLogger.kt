/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import platform.Foundation.*

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
open class NSLogger: PrintLogger() {

    override fun printLog(message: String) {
        NSLog(message)
    }
}
