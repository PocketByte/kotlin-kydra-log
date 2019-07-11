/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
open class PrintLogger: AbsPrintLogger() {

    private val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")

    override fun printLog(message: String, vararg arguments: Any) {
        println(format("${timestamp()} $message", *arguments))
    }

    override fun stackTrace(exception: Throwable): String {
        return exception.stackTrace.joinToString("\n")
    }

    protected open fun timestamp(): String {
        return dateFormat.format(Date())
    }

}
