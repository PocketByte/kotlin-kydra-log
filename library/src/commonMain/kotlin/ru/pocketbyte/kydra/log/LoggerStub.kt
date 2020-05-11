/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Logger that do nothing. Even functions call.
 */
class LoggerStub: Logger {

    override fun log(level: LogLevel, tag: String?, message: String) {
        // Do nothing
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        // Do nothing
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        // Do nothing
    }
}