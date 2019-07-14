package ru.pocketbyte.hydra.log

/**
 * Base implementation of PrintLogger.
 */
abstract class AbsPrintLogger: Logger {

    protected abstract fun printLog(message: String, vararg arguments: Any)

    /**
     * Gets stack trace string from Throwable
     */
    protected abstract fun stackTrace(exception: Throwable): String

    /**
     * Gets qualified name from Throwable
     */
    protected abstract fun qualifiedName(exception: Throwable): String

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        printLog(logToString(level, tag, message), *arguments)
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        if (exception.message != null) {
            printLog(logToString(level, tag, "%s: %s\n%s"),
                    qualifiedName(exception),
                    exception.message!!,
                    stackTrace(exception))
        } else {
            printLog(logToString(level, tag, "%s\n%s"),
                    qualifiedName(exception),
                    stackTrace(exception))
        }
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        log(level, tag, function())
    }

    protected open fun logToString(level: LogLevel, tag: String?, message: String): String {
        val builder = StringBuilder()

        builder.append(
                when(level) {
                    LogLevel.INFO -> "I"
                    LogLevel.DEBUG -> "D"
                    LogLevel.WARNING -> "W"
                    LogLevel.ERROR -> "E"
                }
        )

        builder.append("/")

        if (tag?.isNotEmpty() == true)
            builder.append(tag)

        builder.append(": ")
        builder.append(message)

        return builder.toString()
    }
}