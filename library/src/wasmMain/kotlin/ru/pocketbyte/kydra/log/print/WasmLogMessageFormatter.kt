package ru.pocketbyte.kydra.log.print

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * WebAssembly implementation of [AbsLogMessageFormatter].
 * Formats timestamps as `HH:mm:ss` using [kotlin.time.Clock] and appends throwables
 * using [Throwable.stackTraceToString].
 */
@OptIn(ExperimentalTime::class)
class WasmLogMessageFormatter : AbsLogMessageFormatter() {

    /** Returns the current UTC time formatted as `HH:mm:ss`. */
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

    /**
     * Appends the full stack trace of [throwable] via [Throwable.stackTraceToString].
     * @return This builder, for chaining
     */
    override fun StringBuilder.appendThrowable(throwable: Throwable): StringBuilder {
        append(throwable.stackTraceToString())
        return this
    }
}
