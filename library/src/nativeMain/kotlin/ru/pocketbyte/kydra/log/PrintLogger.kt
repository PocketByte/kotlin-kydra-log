/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlinx.cinterop.*
import platform.posix.*
import kotlin.experimental.ExperimentalNativeApi

@Suppress("DeprecatedCallableAddReplaceWith", "DEPRECATION_ERROR")
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class, UnsafeNumber::class)
open class PrintLogger: AbsPrintLogger() {

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
        return exception.getStackTrace().joinToString("\n")
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
        return memScoped {
            val timeVar = alloc<time_tVar>()
            time(timeVar.ptr)
            ctime(timeVar.ptr)?.toKString()?.replace("[\n|\r]".toRegex(), "") ?: ""
        }
    }
}