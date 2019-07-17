/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import java.text.SimpleDateFormat
import java.util.*

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
open class PrintLogger: AbsPrintLogger() {

    private val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")

    override fun printLog(message: String) {
        println("${timestamp()} $message")
    }

    override fun stackTrace(exception: Throwable): String {
        return exception.stackTrace.joinToString("\n")
    }

    override fun qualifiedName(exception: Throwable): String {
        return exception::class.qualifiedName!!
    }

    protected open fun timestamp(): String {
        return dateFormat.format(Date())
    }

}
