package ru.pocketbyte.kydra.log.collection

import ru.pocketbyte.kydra.log.Logger

/**
 * A [LoggersSet] that allows new loggers to be added after construction.
 * Loggers cannot be removed once added.
 *
 * @property loggers The current set of loggers, including any added via [addLogger].
 */
open class ExtendableLoggersSet private constructor(
    loggers: Set<Logger>
) : LoggersSet(loggers) {

    override val loggers: MutableSet<Logger> = loggers.toMutableSet()

    /** Creates an extendable loggers set from a vararg list of loggers. */
    constructor(vararg loggers: Logger): this(mutableSetOf(*loggers))

    /**
     * Adds [logger] to this set. From this point, all log records will also be forwarded to it.
     * @param logger The logger to add
     */
    fun addLogger(logger: Logger) {
        loggers.add(logger)
    }
}