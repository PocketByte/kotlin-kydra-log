package ru.pocketbyte.hydra.log

import kotlinx.cinterop.*
import platform.Foundation.*

class IosLogger(
        level: LogLevel? = null,
        tags: Set<String?>? = null
): AbsFilteredLogger(level, tags) {

    override fun printLog(level: LogLevel, tag: String?, function: () -> String) {
        throw RuntimeException("This method shouldn't be called")
    }

    override fun printLog(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        if (arguments.isEmpty())
            NSLog(merge(level, tag, message, null))
        else
            NSLog(merge(level, tag, format(message, *arguments), null))
    }

    override fun printLog(level: LogLevel, tag: String?, exception: Throwable) {
        NSLog(merge(level, tag, null, exception))
    }

    private fun merge(level: LogLevel, tag: String?, message: String?,
                      exception: Throwable?): String {
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

        if (message?.isNotEmpty() == true)
            builder.append(message)
        else if(exception != null) {
            builder.append(exception::class.qualifiedName)

            if (exception.message != null)
                builder.append(": ").append(exception.message!!)

            exception.getStackTrace().forEach {
                builder.append("\n").append(it)
            }
        }

        return builder.toString()
    }

}
