package ru.pocketbyte.hydra.log

import android.util.Log

/**
 * Android implementation of Logger that writes logs using LogCat.
 */
class AndroidLogger: Logger {

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        val logMessage = String.format(message, *arguments)

        when(level) {
            LogLevel.INFO -> Log.i(tag ?: "", logMessage)
            LogLevel.DEBUG -> Log.d(tag ?: "", logMessage)
            LogLevel.WARNING -> Log.w(tag ?: "", logMessage)
            LogLevel.ERROR -> Log.e(tag ?: "", logMessage)
        }
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        when(level) {
            LogLevel.INFO -> Log.i(tag ?: "", "", exception)
            LogLevel.DEBUG -> Log.d(tag ?: "", "", exception)
            LogLevel.WARNING -> Log.w(tag ?: "", "", exception)
            LogLevel.ERROR -> Log.e(tag ?: "", "", exception)
        }
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        log(level, tag, function())
    }

}