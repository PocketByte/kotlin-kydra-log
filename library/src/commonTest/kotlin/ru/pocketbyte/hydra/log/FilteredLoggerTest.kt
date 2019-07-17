/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

abstract class FilteredLoggerTest {

    @Test
    fun testFilterAll() {
        val logger = FilteredLogger(LoggerImpl())

        LogLevel.values().forEach { level ->
            assertEquals(true, logger.filter(level, null),
                    "Level ${level.name} not satisfied but should")
        }

        assertEquals(true, logger.filter(LogLevel.INFO, "TAG"))
        assertEquals(true, logger.filter(LogLevel.DEBUG, "TEST"))
    }

    @Test
    fun testFilterLevels() {
        var logger = FilteredLogger(LoggerImpl(), level = null)
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLogger(LoggerImpl(), LogLevel.DEBUG)
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLogger(LoggerImpl(), LogLevel.INFO)
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))
        assertEquals(true, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLogger(LoggerImpl(), LogLevel.WARNING)
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))
        assertEquals(false, logger.filter(LogLevel.INFO, null))
        assertEquals(true, logger.filter(LogLevel.WARNING, null))
        assertEquals(true, logger.filter(LogLevel.ERROR, null))

        logger = FilteredLogger(LoggerImpl(), LogLevel.ERROR)
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

        var logger = FilteredLogger(LoggerImpl(), tags = null)
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(true, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLogger(LoggerImpl(), tags = setOf(tag1, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLogger(LoggerImpl(), tags = setOf(tag2, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLogger(LoggerImpl(), tags = setOf(tag3, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag1))
        assertEquals(false, logger.filter(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.filter(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.filter(LogLevel.DEBUG, null))

        logger = FilteredLogger(LoggerImpl(), tags = setOf(null, tag4))
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
            val filteredLogger = FilteredLogger(logger, expectLevel)

            assertNull(logger.calledMethod)

            LogLevel.values().forEach { level ->
                logger.reset()
                filteredLogger.log(level, null, "message")

                if (expectLevel.priority <= level.priority)
                    assertEquals(Method.MESSAGE, logger.calledMethod,
                            "Called wrong method")
                else
                    assertNull(logger.calledMethod,
                            "Level ${level.name} satisfied " +
                                    "for filtering by ${expectLevel.name} but shouldn't")

                logger.reset()
                filteredLogger.log(level, null, RuntimeException())

                if (expectLevel.priority <= level.priority)
                    assertEquals(Method.EXCEPTION, logger.calledMethod,
                            "Called wrong method")
                else
                    assertNull(logger.calledMethod,
                            "Level ${level.name} satisfied " +
                                    "for filtering by ${expectLevel.name} but shouldn't")


                logger.reset()
                filteredLogger.log(level, null) { "message" }

                if (expectLevel.priority <= level.priority)
                    assertEquals(Method.FUNCTION, logger.calledMethod,
                            "Called wrong method")
                else
                    assertNull(logger.calledMethod,
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
        val filteredLogger = FilteredLogger(logger, tags = setOf(gudTag))

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, gudTag, "some message")
        assertEquals(Method.MESSAGE, logger.calledMethod)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, badTag, "some message")
        assertNull(logger.calledMethod)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, null, "some message")
        assertNull(logger.calledMethod)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, gudTag, RuntimeException())
        assertEquals(Method.EXCEPTION, logger.calledMethod)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, badTag, RuntimeException())
        assertNull(logger.calledMethod)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, null, RuntimeException())
        assertNull(logger.calledMethod)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, gudTag) { "message" }
        assertEquals(Method.FUNCTION, logger.calledMethod)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, badTag) { "message" }
        assertNull(logger.calledMethod)

        logger.reset()
        filteredLogger.log(LogLevel.DEBUG, null) { "message" }
        assertNull(logger.calledMethod)
    }

    private enum class Method {
        MESSAGE,
        EXCEPTION,
        FUNCTION
    }

    private class LoggerImpl :Logger {

        val calledMethod: Method?
            get() { return _calledMethod }

        private var _calledMethod: Method? = null

        override fun log(level: LogLevel, tag: String?, message: String) {
            _calledMethod = Method.MESSAGE
        }

        override fun log(level: LogLevel, tag: String?, exception: Throwable) {
            _calledMethod = Method.EXCEPTION
        }

        override fun log(level: LogLevel, tag: String?, function: () -> String) {
            _calledMethod = Method.FUNCTION
        }

        fun reset() {
            _calledMethod = null
        }

    }
}