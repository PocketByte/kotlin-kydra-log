/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

abstract class AbsLogger: Logger {

    override fun log(level: LogLevel, tag: String?, message: Any) {
        when(message) {
            is Throwable -> log(level, tag, exception = message)
            is LazyMessage -> log(level, tag, message = message.getMessage())
            else -> log(level, tag, string = message.toString())
        }
    }

    abstract fun log(level: LogLevel, tag: String?, string: String)
    abstract fun log(level: LogLevel, tag: String?, exception: Throwable)
}
