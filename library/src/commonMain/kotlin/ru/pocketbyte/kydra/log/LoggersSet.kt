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
): Logger() {

    val isEmpty: Boolean
        get() = loggers.isEmpty()

    override val filter: (level: LogLevel, tag: String?) -> Boolean = { level, tag ->
        loggers.find { it.filter?.invoke(level, tag) != false } != null
    }

    constructor(vararg loggers: Logger): this(setOf(*loggers))

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        this.loggers.forEach {
            it.log(level, tag, message)
        }
    }
}