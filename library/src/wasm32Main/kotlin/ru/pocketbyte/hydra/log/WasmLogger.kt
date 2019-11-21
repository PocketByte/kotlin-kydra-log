/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import kotlinx.cinterop.*

open class WasmLogger: AbsPrintLogger() {

    override fun printLog(message: String) {
        println(message)
    }

    override fun stackTrace(exception: Throwable): String {
        return exception.getStackTrace().joinToString("\n")
    }

    override fun qualifiedName(exception: Throwable): String {
        return exception::class.qualifiedName!!
    }
}