package ru.pocketbyte.hydra.log

/**
 * The set of loggers wrapped into single object
 */
class LoggerSet(
        val loggers: Array<Logger>,
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