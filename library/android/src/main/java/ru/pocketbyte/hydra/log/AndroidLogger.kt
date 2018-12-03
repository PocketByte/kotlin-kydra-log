package ru.pocketbyte.hydra.log

import android.util.Log

class AndroidLogger(
        level: LogLevel? = null,
        tags: Set<String?>? = null
): AbsFilteredLogger(level, tags, calculateFunctions = true) {

    override fun printLog(level: LogLevel, tag: String?, function: () -> String) {
        throw RuntimeException("This method shouldn't be called")
    }

    override fun printLog(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        if (arguments.isEmpty())
            printLogInner(level, tag, message)
        else
            printLogInner(level, tag, String.format(message, *arguments))
    }

    override fun printLog(level: LogLevel, tag: String?, exception: Throwable) {
        when(level) {
            LogLevel.INFO -> Log.i(tag ?: "", "", exception)
            LogLevel.DEBUG -> Log.d(tag ?: "", "", exception)
            LogLevel.WARNING -> Log.w(tag ?: "", "", exception)
            LogLevel.ERROR -> Log.e(tag ?: "", "", exception)
        }
    }

    private fun printLogInner(level: LogLevel, tag: String?, message: String) {
        when(level) {
            LogLevel.INFO -> Log.i(tag ?: "", message)
            LogLevel.DEBUG -> Log.d(tag ?: "", message)
            LogLevel.WARNING -> Log.w(tag ?: "", message)
            LogLevel.ERROR -> Log.e(tag ?: "", message)
        }
    }

}