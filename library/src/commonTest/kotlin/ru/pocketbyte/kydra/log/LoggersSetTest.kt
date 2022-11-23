/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

abstract class LoggersSetTest {

    @Test
    fun testLogMessage() {
        val logger1 = LoggerImpl()
        val logger2 = LoggerImpl()
        val loggerSet = LoggersSet(setOf(logger1, logger2))

        val level = LogLevel.DEBUG
        val tag = "TEST_1"
        val message = "Some message!"

        assertNull(logger1.message)
        assertNull(logger2.message)

        loggerSet.log(level, tag, message)

        assertEquals(level, logger1.level)
        assertEquals(level, logger2.level)
        assertEquals(tag, logger1.tag)
        assertEquals(tag, logger2.tag)
        assertEquals(message, logger1.message)
        assertEquals(message, logger2.message)

    }

    @Test
    fun testLogException() {
        val logger1 = LoggerImpl()
        val logger2 = LoggerImpl()
        val loggerSet = LoggersSet(setOf(logger1, logger2))

        val level = LogLevel.WARNING
        val tag = "TEST_2_WARN"
        val exception = RuntimeException("some msg", IllegalArgumentException())

        assertNull(logger1.message)
        assertNull(logger2.message)

        loggerSet.log(level, tag, exception)

        assertEquals(level, logger1.level)
        assertEquals(level, logger2.level)
        assertEquals(tag, logger1.tag)
        assertEquals(tag, logger2.tag)
        assertEquals(exception, logger1.message)
        assertEquals(exception, logger2.message)
    }

    private class LoggerImpl: Logger {

        var level: LogLevel? = null
            private set
        var tag: String? = null
            private set
        var message: Any? = null
            private set

        override fun log(level: LogLevel, tag: String?, message: Any) {
            this.level = level
            this.tag = tag
            this.message = message
        }
    }
}