package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.TestLogger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
        val loggerTransform = logger.withTag(default = "default")

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
        val loggerTransform = logger.withTag(default = "default")

        val level = LogLevel.WARNING
        val tag = null
        val message = "Some message!"

        loggerTransform.log(level, tag) { message }

        assertEquals(level, logger.level)
        assertEquals("default", logger.tag)
        assertEquals(message, logger.message)
    }

    @Test
    fun testWithTagPrefixApplied() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag(prefix = "pre-")

        loggerTransform.log(LogLevel.DEBUG, "TAG") { "" }

        assertEquals("pre-TAG", logger.tag)
    }

    @Test
    fun testWithTagPostfixApplied() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag(postfix = "-post")

        loggerTransform.log(LogLevel.DEBUG, "TAG") { "" }

        assertEquals("TAG-post", logger.tag)
    }

    @Test
    fun testWithTagPrefixAndPostfixApplied() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag(prefix = "pre-", postfix = "-post")

        loggerTransform.log(LogLevel.DEBUG, "TAG") { "" }

        assertEquals("pre-TAG-post", logger.tag)
    }

    @Test
    fun testWithTagNullTagWithPrefixRemainsNull() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag(prefix = "pre-")

        loggerTransform.log(LogLevel.DEBUG, null) { "" }

        assertEquals(null, logger.tag)
    }

    @Test
    fun testWithTagNullTagWithDefaultAndPrefix() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag(prefix = "pre-", default = "default")

        loggerTransform.log(LogLevel.DEBUG, null) { "" }

        assertEquals("pre-default", logger.tag)
    }

    @Test
    fun testWithTagNullTagWithDefaultAndPostfix() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag(default = "default", postfix = "-post")

        loggerTransform.log(LogLevel.DEBUG, null) { "" }

        assertEquals("default-post", logger.tag)
    }

    @Test
    fun testWithTagNullTagWithDefaultPrefixAndPostfix() {
        val logger = TestLogger()
        val loggerTransform = logger.withTag(prefix = "pre-", default = "default", postfix = "-post")

        loggerTransform.log(LogLevel.DEBUG, null) { "" }

        assertEquals("pre-default-post", logger.tag)
    }

    @Test
    fun testFilterIsNullWhenInnerLoggerHasNoFilter() {
        val tagTransform = LoggerTagTransform(TestLogger()) { "PREFIX_$it" }
        assertNull(tagTransform.filter)
    }

    @Test
    fun testFilterTransformsTagBeforeCheck() {
        val innerLogger = FilteredLoggerWrapper(TestLogger(), tags = setOf("PREFIX_TAG"))
        val tagTransform = LoggerTagTransform(innerLogger) { "PREFIX_$it" }

        assertEquals(true, tagTransform.filter?.invoke(LogLevel.DEBUG, "TAG"))
        assertEquals(false, tagTransform.filter?.invoke(LogLevel.DEBUG, "OTHER"))
    }

    @Test
    fun testFilterTransformsNullTagBeforeCheck() {
        val innerLogger = FilteredLoggerWrapper(TestLogger(), tags = setOf("DEFAULT"))
        val tagTransform = LoggerTagTransform(innerLogger) { it ?: "DEFAULT" }

        assertEquals(true, tagTransform.filter?.invoke(LogLevel.DEBUG, null))
        assertEquals(false, tagTransform.filter?.invoke(LogLevel.DEBUG, "OTHER"))
    }

    @Test
    fun testFilterAppliedOnLog() {
        val innerTestLogger = TestLogger()
        val innerLogger = FilteredLoggerWrapper(innerTestLogger, tags = setOf("PREFIX_TAG"))
        val tagTransform = LoggerTagTransform(innerLogger) { "PREFIX_$it" }

        tagTransform.log(LogLevel.DEBUG, "TAG") { "accepted" }
        assertEquals("PREFIX_TAG", innerTestLogger.tag)
        assertEquals("accepted", innerTestLogger.message)

        tagTransform.log(LogLevel.DEBUG, "OTHER") { "rejected" }
        assertEquals("PREFIX_TAG", innerTestLogger.tag)
        assertEquals("accepted", innerTestLogger.message)
    }
}