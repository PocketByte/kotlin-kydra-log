package ru.pocketbyte.hydra.log

/**
 * @author Shurygin Denis
 */
class JsLogger : Logger {

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        when(level) {
            LogLevel.INFO -> console.info(logToString(tag, message), *arguments)
            LogLevel.DEBUG -> console.log("DEBUG/${logToString(tag, message)}", *arguments)
            LogLevel.WARNING -> console.warn(logToString(tag, message), *arguments)
            LogLevel.ERROR ->console.error(logToString(tag, message), *arguments)
        }
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        val logMessage = StringBuilder()

        if (tag?.isNotEmpty() == true)
            logMessage.append(tag).append(": ")

        when(level) {
            LogLevel.INFO -> console.info(logMessage.toString(), exception)
            LogLevel.DEBUG -> console.log("DEBUG/$logMessage", exception)
            LogLevel.WARNING -> console.warn(logMessage.toString(), exception)
            LogLevel.ERROR ->console.error(logMessage.toString(), exception)
        }
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        log(level, tag, function())
    }

    private fun logToString(tag: String?, message: String): String {
        val builder = StringBuilder()

        if (tag?.isNotEmpty() == true)
            builder.append(tag).append(": ")

        builder.append(message)

        return builder.toString()
    }
}