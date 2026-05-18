package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.Logger

actual abstract class InitializableLogger<LoggerType: Logger>
    : AbsLoggerWrapper<LoggerType>() {

    actual override val logger: LoggerType
        get() = innerLogger ?: defaultLogger

    actual val isInitialized: Boolean
        get() = innerLogger != null

    protected actual abstract val defaultLogger: LoggerType

    private var innerLogger: LoggerType? = null

    actual open fun init(logger: LoggerType) {
        if (innerLogger != null)
            throw IllegalStateException("Logger already initialized")
        innerLogger = logger
    }
}