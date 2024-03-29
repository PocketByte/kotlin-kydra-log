/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Logger that do nothing. Even functions call.
 */
class LoggerStub: Logger() {

    override val filter: ((level: LogLevel, tag: String?) -> Boolean) = { _, _ -> false }

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        // Do nothing
    }
}