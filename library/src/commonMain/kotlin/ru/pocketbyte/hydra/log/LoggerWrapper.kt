package ru.pocketbyte.hydra.log

open class LoggerWrapper(
        protected val logger: Logger
): Logger {

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        logger.log(level, tag, message, *arguments)
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        logger.log(level, tag, exception)
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        logger.log(level, tag, function)
    }
}