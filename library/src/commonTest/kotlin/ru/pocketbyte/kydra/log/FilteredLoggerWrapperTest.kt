/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FilteredLoggerWrapperTest {

    @Test
    fun testFilterAll() {
        val logger = FilteredLoggerWrapper(LoggerImpl())

        LogLevel.values().forEach { level ->
            assertEquals(true, logger.filter(level, null),
                    "Level ${level.name} not satisfied but should")
        }

        assertEquals(true, logger.filter(LogLevel.INFO, "TAG"))
        assertEquals(true, logger.filter(LogLevel.DEBUG, "TEST"))
    }

    @Test
    fun testFilterLevels() {
        var logger = FilteredLoggerWrapper(LoggerImpl(), level = null)
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLoggerWrapper(LoggerImpl(), LogLevel.DEBUG)
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLoggerWrapper(LoggerImpl(), LogLevel.INFO)
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLoggerWrapper(LoggerImpl(), LogLevel.WARNING)
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))
        assertEquals(false, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLoggerWrapper(LoggerImpl(), LogLevel.ERROR)
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))
        assertEquals(false, logger.filter(LogLevel.INFO, null))
        assertEquals(false, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))
    }

    @Test
    fun testFilterTags() {
        val tag1 = "tag1_a"
        val tag2 = "tag2_b"
        val tag3 = "tag3_f"
        val tag4 = "tag4"

        var logger = FilteredLoggerWrapper(LoggerImpl(), tags = null)
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLoggerWrapper(LoggerImpl(), tags = setOf(tag1, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLoggerWrapper(LoggerImpl(), tags = setOf(tag2, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLoggerWrapper(LoggerImpl(), tags = setOf(tag3, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLoggerWrapper(LoggerImpl(), tags = setOf(null, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))
    }

    @Test
    fun testLevelFilter() {
        LogLevel.values().forEach { expectLevel ->
            val logger = LoggerImpl()
            val filteredLogger = FilteredLoggerWrapper(logger, expectLevel)

            assertNull(logger.lastMessage)

            LogLevel.values().forEach { level ->
                logger.reset()
                val message = "Level: ${level.name}"
                filteredLogger.log(level, null, message)

                if (expectLevel.priority <= level.priority)
                    assertEquals(message, logger.lastMessage, "Called wrong method")
                else
                    assertNull(logger.lastMessage,
                            "Level ${level.name} satisfied " +
                                    "for filtering by ${expectLevel.name} but shouldn't")
            }
        }
    }

    @Test
    fun testTagFilter() {
        val gudTag = "gudTag_153674"
        val badTag = "badTag_984234"

        val logger = LoggerImpl()
        val filteredLogger = FilteredLoggerWrapper(logger, tags = setOf(gudTag))

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, gudTag, "some message")
        assertEquals("some message", logger.lastMessage)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, badTag, "some debug message")
        assertNull(logger.lastMessage)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, null, "some message")
        assertNull(logger.lastMessage)
    }

    private class LoggerImpl :Logger() {

        var lastMessage: Any? = null
            private set

        override fun doLog(level: LogLevel, tag: String?, message: Any) {
            lastMessage = message
        }

        fun reset() {
            lastMessage = null
        }

    }
}