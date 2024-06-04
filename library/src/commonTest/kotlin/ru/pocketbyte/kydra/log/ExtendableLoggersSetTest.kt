package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ExtendableLoggersSetTest {

    @Test
    fun testLogMessage() {
        val logger1 = TestLogger()
        val logger2 = TestLogger()
        val loggerSet = ExtendableLoggersSet(logger1)

        var level = LogLevel.DEBUG
        var tag = "TEST_1"
        var message = "Some message!"

        assertNull(logger1.message)
        assertNull(logger2.message)

        loggerSet.log(level, tag, message)

        assertEquals(level, logger1.level)
        assertEquals(tag, logger1.tag)
        assertEquals(message, logger1.message)

        assertNull(logger2.level)
        assertNull(logger2.tag)
        assertNull(logger2.message)

        level = LogLevel.INFO
        tag = "TEST_2"
        message = "Another message!"

        loggerSet.addLogger(logger2)
        loggerSet.log(level, tag, message)

        assertEquals(level, logger1.level)
        assertEquals(level, logger2.level)
        assertEquals(tag, logger1.tag)
        assertEquals(tag, logger2.tag)
        assertEquals(message, logger1.message)
        assertEquals(message, logger2.message)
    }
}