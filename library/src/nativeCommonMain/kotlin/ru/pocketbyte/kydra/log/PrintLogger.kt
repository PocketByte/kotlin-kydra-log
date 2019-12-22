/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlinx.cinterop.*
import platform.posix.*

open class PrintLogger: AbsPrintLogger() {

    override fun printLog(message: String) {
        println("${timestamp()} $message")
    }

    override fun stackTrace(exception: Throwable): String {
        return exception.getStackTrace().joinToString("\n")
    }

    override fun qualifiedName(exception: Throwable): String {
        return exception::class.qualifiedName!!
    }

    protected open fun timestamp(): String {
        return memScoped {
            val time_t = alloc<time_tVar>()
            time(time_t.ptr)
            ctime(time_t.ptr)?.toKString()?.replace("[\n|\r]".toRegex(), "") ?: ""
        }
    }
}