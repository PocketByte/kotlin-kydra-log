package ru.pocketbyte.kydra.log.collection

import ru.pocketbyte.kydra.log.Logger

/**
 * The set of loggers wrapped into single Logger object with opportunity to extend provided set
 * with additional loggers. Loggers removing not allowed!
 *
 * @property loggers Initial set of loggers
 *
 * @constructor Creates Extendable Loggers set.
 */
open class ExtendableLoggersSet private constructor(
    loggers: Set<Logger>
) : LoggersSet(loggers) {

    override val loggers: MutableSet<Logger> = loggers.toMutableSet()

    constructor(vararg loggers: Logger): this(mutableSetOf(*loggers))

    fun addLogger(logger: Logger) {
        loggers.add(logger)
    }
}