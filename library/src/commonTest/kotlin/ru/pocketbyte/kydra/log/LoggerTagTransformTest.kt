package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class LoggerTagTransformTest {

    @Test
    fun testWrapper() {
        val logger = TestLogger()
        val loggerTagTransform = LoggerTagTransform(logger) { it }

        assertSame(logger, loggerTagTransform.logger)
    }

    @Test
    fun testTagTransform() {
        val loggerTransform = LoggerTagTransform(TestLogger()) {
            "$it-tagPostfix"
        }

        val level = LogLevel.DEBUG
        val tag = "TEST_1"
        val message = "Some message!"

        loggerTransform.log(level, tag) { message }

        assertEquals(level, loggerTransform.logger.level)
        assertEquals("TEST_1-tagPostfix", loggerTransform.logger.tag)
        assertEquals(message, loggerTransform.logger.message)
    }

    @Test
    fun testWithTagTagNotChanged() {
        val loggerTransform = TestLogger().withTag("default")

        val level = LogLevel.WARNING
        val tag = "TEST_1"
        val message = "Some message!"

        loggerTransform.log(level, tag) { message }

        assertEquals(level, loggerTransform.logger.level)
        assertEquals(tag, loggerTransform.logger.tag)
        assertEquals(message, loggerTransform.logger.message)
    }

    @Test
    fun testWithTagTagChanged() {
        val loggerTransform = TestLogger().withTag("default")

        val level = LogLevel.WARNING
        val tag = null
        val message = "Some message!"

        loggerTransform.log(level, tag) { message }

        assertEquals(level, loggerTransform.logger.level)
        assertEquals("default", loggerTransform.logger.tag)
        assertEquals(message, loggerTransform.logger.message)
    }

}