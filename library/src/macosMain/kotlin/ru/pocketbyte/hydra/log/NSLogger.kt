/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import platform.Foundation.*

/**
 * iOS implementation of Logger that writes logs using NSLog.
 */
open class NSLogger: AbsPrintLogger() {

    override fun printLog(message: String, vararg arguments: Any) {
        val macOsFormat = message.replace("%s", "%@")
        when(arguments.size) {
            0 -> NSLog(macOsFormat)
            1 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString)
            2 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString)
            3 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString,
                            arguments[2].toString() as NSString)
            4 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString,
                            arguments[2].toString() as NSString,
                            arguments[3].toString() as NSString)
            5 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString,
                            arguments[2].toString() as NSString,
                            arguments[3].toString() as NSString,
                            arguments[4].toString() as NSString)
            6 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString,
                            arguments[2].toString() as NSString,
                            arguments[3].toString() as NSString,
                            arguments[4].toString() as NSString,
                            arguments[5].toString() as NSString)
            7 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString,
                            arguments[2].toString() as NSString,
                            arguments[3].toString() as NSString,
                            arguments[4].toString() as NSString,
                            arguments[5].toString() as NSString,
                            arguments[6].toString() as NSString)
            8 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString,
                            arguments[2].toString() as NSString,
                            arguments[3].toString() as NSString,
                            arguments[4].toString() as NSString,
                            arguments[5].toString() as NSString,
                            arguments[6].toString() as NSString,
                            arguments[7].toString() as NSString)
            9 -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString,
                            arguments[2].toString() as NSString,
                            arguments[3].toString() as NSString,
                            arguments[4].toString() as NSString,
                            arguments[5].toString() as NSString,
                            arguments[6].toString() as NSString,
                            arguments[7].toString() as NSString,
                            arguments[8].toString() as NSString)
            else -> NSLog(macOsFormat,
                            arguments[0].toString() as NSString,
                            arguments[1].toString() as NSString,
                            arguments[2].toString() as NSString,
                            arguments[3].toString() as NSString,
                            arguments[4].toString() as NSString,
                            arguments[5].toString() as NSString,
                            arguments[6].toString() as NSString,
                            arguments[7].toString() as NSString,
                            arguments[8].toString() as NSString,
                            arguments[9].toString() as NSString)
        }
    }

    override fun stackTrace(exception: Throwable): String {
        return exception.getStackTrace().joinToString("\n")
    }
}
