package ru.pocketbyte.kydra.log.collection

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger

/**
 * A [Logger] that forwards every log record to a set of loggers.
 *
 * The combined [filter] passes a record if at least one logger in the set would accept it.
 * If all loggers have no filter, the combined filter is `null` and all records are passed through.
 *
 * @property loggers The set of loggers to which log records are forwarded.
 */
open class LoggersSet(
    loggers: Set<Logger>
): Logger() {

    protected open val loggers: Set<Logger> = loggers.toSet()

    /** Returns `true` if this set contains no loggers. */
    val isEmpty: Boolean
        get() = loggers.isEmpty()

    private val combinedFilter: (level: LogLevel, tag: String?) -> Boolean = { level, tag ->
        loggers.find { it.filter?.invoke(level, tag) != false } != null
    }

    /**
     * Returns `null` if none of the loggers in the set define a filter.
     * Otherwise, returns a combined filter that passes a record if at least one logger accepts it.
     */
    override val filter: ((level: LogLevel, tag: String?) -> Boolean)? get() =
        if (loggers.all { it.filter == null }) {
            null
        } else {
            combinedFilter
        }

    /** Creates a loggers set from a vararg list of loggers. */
    constructor(vararg loggers: Logger): this(setOf(*loggers))

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        this.loggers.forEach {
            it.log(level, tag) { message }
        }
    }
}