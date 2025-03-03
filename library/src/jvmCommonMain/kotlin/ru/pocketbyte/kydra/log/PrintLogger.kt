/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import java.text.SimpleDateFormat
import java.util.*

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
@Suppress("DeprecatedCallableAddReplaceWith", "DEPRECATION_ERROR")
open class PrintLogger: AbsPrintLogger() {

    private val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")

    @Deprecated(
        "Do not override this method. " +
                "To implement custom PrintLogger use SimplePrintLogger.",
        level = DeprecationLevel.ERROR
    )
    override fun printLog(message: String) {
        println("${timestamp()} $message")
    }

    @Deprecated(
        "Do not override this method. " +
                "To implement custom PrintLogger use SimplePrintLogger.",
        level = DeprecationLevel.ERROR
    )
    override fun stackTrace(exception: Throwable): String {
        return exception.stackTrace.joinToString("\n")
    }

    @Deprecated(
        "Do not override this method. " +
                "To implement custom PrintLogger use SimplePrintLogger.",
        level = DeprecationLevel.ERROR
    )
    override fun qualifiedName(exception: Throwable): String {
        return exception::class.qualifiedName ?: "unknown"
    }

    @Deprecated(
        "Do not override this method. " +
                "To implement custom PrintLogger use SimplePrintLogger.",
        level = DeprecationLevel.ERROR
    )
    protected open fun timestamp(): String {
        return dateFormat.format(Date())
    }

}
