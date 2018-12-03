package ru.pocketbyte.hydra.log

actual open class AbsFilteredLoggerTestImpl: AbsFilteredLoggerTest() {

    actual override fun random(): Long {
        return System.currentTimeMillis()
    }

}