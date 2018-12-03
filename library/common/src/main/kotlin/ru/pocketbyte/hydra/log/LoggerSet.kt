package ru.pocketbyte.hydra.log

/**
 * The set of loggers wrapped into single object
 */
class LoggerSet(
        val loggers: Array<Logger>,
        level: LogLevel? = null,
        tags: Set<String?>? = null,
        calculateFunctions: Boolean = false
): AbsFilteredLogger(level, tags, calculateFunctions) {

    override fun printLog(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        this.loggers.forEach {
            it.log(level, tag, message, arguments)
        }
    }

    override fun printLog(level: LogLevel, tag: String?, exception: Throwable) {
        this.loggers.forEach {
            it.log(level, tag, exception)
        }
    }

    override fun printLog(level: LogLevel, tag: String?, function: () -> String) {
        this.loggers.forEach {
            it.log(level, tag, function)
        }
    }

}