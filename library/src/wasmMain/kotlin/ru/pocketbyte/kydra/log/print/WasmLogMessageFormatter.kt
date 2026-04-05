package ru.pocketbyte.kydra.log.print

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class WasmLogMessageFormatter : AbsLogMessageFormatter() {

    override fun getTimeStamp(): String {
        val epochSeconds = Clock.System.now().epochSeconds
        val secondsInDay = epochSeconds % 86400
        val hours = secondsInDay / 3600
        val minutes = (secondsInDay % 3600) / 60
        val seconds = secondsInDay % 60
        return "${hours.toString().padStart(2, '0')}:" +
                "${minutes.toString().padStart(2, '0')}:" +
                seconds.toString().padStart(2, '0')
    }

    override fun StringBuilder.appendThrowable(throwable: Throwable): StringBuilder {
        append(throwable.stackTraceToString())
        return this
    }
}
