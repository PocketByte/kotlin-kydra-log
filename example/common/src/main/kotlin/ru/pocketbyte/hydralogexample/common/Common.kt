package ru.pocketbyte.hydralogexample.common

import ru.pocketbyte.hydra.log.*

object Common {

    private const val LOG_TAG = "LogExample"

    fun printInfo(message: String, stackTrace: Boolean) {
        printLog(LogLevel.INFO, message, stackTrace)
    }

    fun printDebug(message: String, stackTrace: Boolean) {
        printLog(LogLevel.DEBUG, message, stackTrace)
    }

    fun printWarning(message: String, stackTrace: Boolean) {
        printLog(LogLevel.WARNING, message, stackTrace)
    }

    fun printError(message: String, stackTrace: Boolean) {
        printLog(LogLevel.ERROR, message, stackTrace)
    }

    private fun printLog(level: LogLevel, message: String, stackTrace: Boolean) {
        if (stackTrace)
            HydraLog.log(level, LOG_TAG, RuntimeException(message))
        else
            HydraLog.log(level, LOG_TAG, message)
    }

}
