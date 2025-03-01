package ru.pocketbyte.kydra.log

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import java.util.Date

class MockDateProvider {
    private var currentTime = Date()

    val currentEpochMilliseconds: Long get() = currentTime.time

    fun plus(duration: Duration) {
        currentTime.time += duration.inWholeMilliseconds
    }

    fun plus(milliseconds: Long) {
        plus(milliseconds.toDuration(DurationUnit.MILLISECONDS))
    }
}
