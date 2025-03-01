/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class FilteredLoggerWrapperTest {

    @Test
    fun testWrapper() {
        val logger = TestLogger()
        val loggerFilter = FilteredLoggerWrapper(logger) { _, _ -> true }

        assertSame(logger, loggerFilter.logger)
    }

    @Test
    fun testFilterAll() {
        val logger = FilteredLoggerWrapper(TestLogger(), level = null, tags = null)

        LogLevel.entries.forEach { level ->
            assertEquals(
                true, logger.filter(level, null),
                "Level ${level.name} not satisfied but should"
            )
        }

        assertEquals(true, logger.filter(LogLevel.INFO, "TAG"))
        assertEquals(true, logger.filter(LogLevel.DEBUG, "TEST"))
    }

    @Test
    fun testFilterLevels() {
        var logger = FilteredLoggerWrapper(TestLogger(), level = null)
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLoggerWrapper(TestLogger(), LogLevel.DEBUG)
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLoggerWrapper(TestLogger(), LogLevel.INFO)
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLoggerWrapper(TestLogger(), LogLevel.WARNING)
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))
        assertEquals(false, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLoggerWrapper(TestLogger(), LogLevel.ERROR)
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

        var logger = FilteredLoggerWrapper(TestLogger(), tags = null)
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLoggerWrapper(TestLogger(), tags = setOf(tag1, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLoggerWrapper(TestLogger(), tags = setOf(tag2, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLoggerWrapper(TestLogger(), tags = setOf(tag3, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLoggerWrapper(TestLogger(), tags = setOf(null, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))
    }

    @Test
    fun testLevelFilter() {
        LogLevel.entries.forEach { expectLevel ->
            LogLevel.entries.forEach { level ->
                val logger = TestLogger()
                val filteredLogger = FilteredLoggerWrapper(logger, expectLevel)

                val message = "Level: ${level.name}"
                filteredLogger.log(level, null) { message }

                if (expectLevel.priority <= level.priority)
                    assertEquals(message, logger.message, "Called wrong method")
                else
                    assertNull(
                        logger.message,
                        "Level ${level.name} satisfied " +
                                "for filtering by ${expectLevel.name} but shouldn't"
                    )
            }
        }
    }

    @Test
    fun testTagFilter() {
        val gudTag = "gudTag_153674"
        val badTag = "badTag_984234"

        var logger = TestLogger()
        var filteredLogger = FilteredLoggerWrapper(logger, tags = setOf(gudTag))
        filteredLogger.log(LogLevel.DEBUG, gudTag) { "some message" }
        assertEquals("some message", logger.message)

        logger = TestLogger()
        filteredLogger = FilteredLoggerWrapper(logger, tags = setOf(gudTag))
        filteredLogger.log(LogLevel.DEBUG, badTag) { "some debug message" }
        assertNull(logger.message)

        logger = TestLogger()
        filteredLogger = FilteredLoggerWrapper(logger, tags = setOf(gudTag))
        filteredLogger.log(LogLevel.DEBUG, null) { "some message" }
        assertNull(logger.message)
    }
}
