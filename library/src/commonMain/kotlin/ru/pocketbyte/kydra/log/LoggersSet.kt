/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * The set of loggers wrapped into single Logger object.
 *
 * @property loggers Set of loggers
 *
 * @constructor Creates Loggers set.
 */
open class LoggersSet(
    private val loggers: Set<Logger>
): AbsLogger() {

    constructor(vararg loggers: Logger): this(setOf(*loggers))

    override fun log(level: LogLevel, tag: String?, string: String) {
        this.loggers.forEach {
            it.log(level, tag, string)
        }
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        this.loggers.forEach {
            it.log(level, tag, exception)
        }
    }

}