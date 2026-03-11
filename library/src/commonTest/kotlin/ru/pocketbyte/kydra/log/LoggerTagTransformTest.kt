package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class LoggerTagTransformTest {

    @Test
    fun testTagTransform() {
        val logger = TestLogger()
        val loggerTransform = LoggerTagTransform(logger) {
            "$it-tagPostfix"
        }

        val level = LogLevel.DEBUG
        val tag = "TEST_1"
        val message = "Some message!"

        loggerTransform.log(level, tag) { message }

        assertEquals(level, logger.level)
        assertEquals("TEST_1-tagPostfix", logger.tag)
        assertEquals(message, logger.message)
    }

    @Test
    fun testWithTagTagNotChanged() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag("default")

        val level = LogLevel.WARNING
        val tag = "TEST_1"
        val message = "Some message!"

        loggerTransform.log(level, tag) { message }

        assertEquals(level, logger.level)
        assertEquals(tag, logger.tag)
        assertEquals(message, logger.message)
    }

    @Test
    fun testWithTagTagChanged() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag("default")

        val level = LogLevel.WARNING
        val tag = null
        val message = "Some message!"

        loggerTransform.log(level, tag) { message }

        assertEquals(level, logger.level)
        assertEquals("default", logger.tag)
        assertEquals(message, logger.message)
    }
}