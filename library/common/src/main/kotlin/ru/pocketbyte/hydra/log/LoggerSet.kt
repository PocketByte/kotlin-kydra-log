package ru.pocketbyte.hydra.log

/**
 * The set of loggers wrapped into single Logger object.
 *
 * @property loggers Set of loggers
 * @property optimizeFunctions True if function should be calculated once for all loggers,
 * false if each function should be handled by each logger independently.
 *
 * @constructor Creates Loggers set.
 */
class LoggerSet(
        val loggers: Set<Logger>,
        val optimizeFunctions: Boolean = true
): Logger {

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        this.loggers.forEach {
            it.log(level, tag, message, arguments)
        }
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        this.loggers.forEach {
            it.log(level, tag, exception)
        }
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        if (optimizeFunctions)
            log(level, tag, function())
        else this.loggers.forEach {
            it.log(level, tag, function)
        }
    }

}