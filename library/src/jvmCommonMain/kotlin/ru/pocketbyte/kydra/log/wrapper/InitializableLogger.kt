package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.Logger
import java.util.concurrent.atomic.AtomicReference

actual abstract class InitializableLogger<LoggerType: Logger>
    : AbsLoggerWrapper<LoggerType>() {

    actual override val logger: LoggerType
        get() = loggerRef.get() ?: defaultLogger

    actual val isInitialized: Boolean
        get() = loggerRef.get() != null

    protected actual abstract val defaultLogger: LoggerType

    private var loggerRef: AtomicReference<LoggerType?> = AtomicReference(null)

    actual open fun init(logger: LoggerType) {
        if (!this.loggerRef.compareAndSet(null, logger)) {
            throw IllegalStateException("Logger already initialized")
        }
    }
}