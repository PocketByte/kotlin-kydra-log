/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

class ThrowExceptionLogger(
        private val exception: Throwable
): Logger {

    constructor(exceptionMessage: String): this(RuntimeException(exceptionMessage))

    override fun log(level: LogLevel, tag: String?, message: String) {
        throw this.exception
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        throw this.exception
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        throw this.exception
    }
}