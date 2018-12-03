package ru.pocketbyte.hydra.log

actual object HydraLog: Logger {

    private var logger: Logger? = null
    private val LOCK = Object()

    actual fun init(logger: Logger) {
        synchronized(this.LOCK) {
            if (this.logger != null)
                throw IllegalStateException("HydraLog already initialized")
            this.logger = logger
        }
    }

    actual override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        this.checkLogger()
        this.logger?.log(level, tag, message, *arguments)
    }

    actual override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        this.checkLogger()
        this.logger?.log(level, tag, exception)
    }

    actual override fun log(level: LogLevel, tag: String?, function: () -> String) {
        this.checkLogger()
        this.logger?.log(level, tag, function)
    }

    private fun checkLogger() {
        if (this.logger == null)
            throw IllegalStateException("HydraLog not initialized." +
                    " Please call init(logger: Logger) before printing.")
    }
}