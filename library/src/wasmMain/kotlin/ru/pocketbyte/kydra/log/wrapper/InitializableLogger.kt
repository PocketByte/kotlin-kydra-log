package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.Logger
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
actual abstract class InitializableLogger<LoggerType: Logger>
    : AbsLoggerWrapper<LoggerType>() {

    actual override val logger: LoggerType
        get() = loggerRef.load() ?: defaultLogger

    actual val isInitialized: Boolean
        get() = loggerRef.load() != null

    private var loggerRef: AtomicReference<LoggerType?> = AtomicReference(null)

    protected actual abstract val defaultLogger: LoggerType

    actual open fun init(logger: LoggerType) {
        if (!this.loggerRef.compareAndSet(null, logger))
            throw IllegalStateException("Logger already initialized")
    }
}