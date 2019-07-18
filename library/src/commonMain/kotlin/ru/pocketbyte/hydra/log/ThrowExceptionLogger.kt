package ru.pocketbyte.hydra.log

class ThrowExceptionLogger(
        private val exception: Throwable
): Logger {

    constructor(exceptionMessage: String): this(RuntimeException(exceptionMessage))

    override fun log(level: LogLevel, tag: String?, message: String) {
        throw this.exception
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        throw this.exception
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        throw this.exception
    }
}