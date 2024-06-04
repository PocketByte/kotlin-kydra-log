package ru.pocketbyte.kydra.log

/**
 * The set of loggers wrapped into single Logger object with opportunity to extend provided set
 * with additional loggers. Loggers removing not allowed!
 *
 * @property loggers Initial set of loggers
 *
 * @constructor Creates Extendable Loggers set.
 */
open class ExtendableLoggersSet private constructor(
    private val mutableSet: MutableSet<Logger>
) : LoggersSet(mutableSet) {

    constructor(vararg loggers: Logger): this(mutableSetOf(*loggers))

    fun addLogger(logger: Logger) {
        mutableSet.add(logger)
    }
}