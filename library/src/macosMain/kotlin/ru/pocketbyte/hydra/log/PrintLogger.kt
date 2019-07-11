package ru.pocketbyte.hydra.log

import kotlinx.cinterop.*
import platform.posix.*

open class PrintLogger: AbsPrintLogger() {

    override fun printLog(message: String, vararg arguments: Any) {
        val nativeFormat = "${timestamp()} $message"
        when(arguments.size) {
            0 -> printf(nativeFormat)
            1 -> printf(nativeFormat,
                    arguments[0].toString())
            2 -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString())
            3 -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString(),
                    arguments[2].toString())
            4 -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString(),
                    arguments[2].toString(),
                    arguments[3].toString())
            5 -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString(),
                    arguments[2].toString(),
                    arguments[3].toString(),
                    arguments[4].toString())
            6 -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString(),
                    arguments[2].toString(),
                    arguments[3].toString(),
                    arguments[4].toString(),
                    arguments[5].toString())
            7 -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString(),
                    arguments[2].toString(),
                    arguments[3].toString(),
                    arguments[4].toString(),
                    arguments[5].toString(),
                    arguments[6].toString())
            8 -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString(),
                    arguments[2].toString(),
                    arguments[3].toString(),
                    arguments[4].toString(),
                    arguments[5].toString(),
                    arguments[6].toString(),
                    arguments[7].toString())
            9 -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString(),
                    arguments[2].toString(),
                    arguments[3].toString(),
                    arguments[4].toString(),
                    arguments[5].toString(),
                    arguments[6].toString(),
                    arguments[7].toString(),
                    arguments[8].toString())
            else -> printf(nativeFormat,
                    arguments[0].toString(),
                    arguments[1].toString(),
                    arguments[2].toString(),
                    arguments[3].toString(),
                    arguments[4].toString(),
                    arguments[5].toString(),
                    arguments[6].toString(),
                    arguments[7].toString(),
                    arguments[8].toString(),
                    arguments[9].toString())
        }
        println()
    }

    override fun stackTrace(exception: Throwable): String {
        return exception.getStackTrace().joinToString("\n")
    }

    protected open fun timestamp(): String {
        return memScoped {
            val time_t = alloc<time_tVar>()
            time(time_t.ptr)
            ctime(time_t.ptr)?.toKString()?.replace("[\n|\r]".toRegex(), "") ?: ""
        }
    }
}