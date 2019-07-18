/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

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

        assertNull(logger1.calledMethod)
        assertNull(logger2.calledMethod)

        loggerSet.log(level, tag, message)

        assertEquals(Method.MESSAGE, logger1.calledMethod)
        assertEquals(Method.MESSAGE, logger2.calledMethod)
        assertEquals(level, logger1.level)
        assertEquals(level, logger2.level)
        assertEquals(tag, logger1.tag)
        assertEquals(tag, logger2.tag)
        assertEquals(message, logger1.message)
        assertEquals(message, logger2.message)
        assertNull(logger1.exception)
        assertNull(logger2.exception)

    }

    @Test
    fun testLogException() {
        val logger1 = LoggerImpl()
        val logger2 = LoggerImpl()
        val loggerSet = LoggersSet(setOf(logger1, logger2))

        val level = LogLevel.WARNING
        val tag = "TEST_2_WARN"
        val exception = RuntimeException("some msg", IllegalArgumentException())

        assertNull(logger1.calledMethod)
        assertNull(logger2.calledMethod)

        loggerSet.log(level, tag, exception)

        assertEquals(Method.EXCEPTION, logger1.calledMethod)
        assertEquals(Method.EXCEPTION, logger2.calledMethod)
        assertEquals(level, logger1.level)
        assertEquals(level, logger2.level)
        assertEquals(tag, logger1.tag)
        assertEquals(tag, logger2.tag)
        assertEquals(exception, logger1.exception)
        assertEquals(exception, logger2.exception)
        assertNull(logger1.message)
        assertNull(logger2.message)
    }

    @Test
    fun testLogFunction() {
        val logger1 = LoggerImpl()
        val logger2 = LoggerImpl()
        val loggerSet = LoggersSet(setOf(logger1, logger2), calcFunctions = false)

        val level = LogLevel.INFO
        val tag = "TEST_3_func"
        val function = { "Result of function" }

        assertNull(logger1.calledMethod)
        assertNull(logger2.calledMethod)

        loggerSet.log(level, tag, function)

        assertEquals(Method.FUNCTION, logger1.calledMethod)
        assertEquals(Method.FUNCTION, logger2.calledMethod)
        assertEquals(level, logger1.level)
        assertEquals(level, logger2.level)
        assertEquals(tag, logger1.tag)
        assertEquals(tag, logger2.tag)
        assertEquals(function, logger1.function)
        assertEquals(function, logger2.function)
        assertNull(logger1.message)
        assertNull(logger2.message)
        assertNull(logger1.exception)
        assertNull(logger2.exception)
    }


    @Test
    fun testLogFunctionCalculation() {
        val level = LogLevel.INFO
        val tag = "TEST_4_func_optimization"
        val function = { "Result of function" }
        val functionResult = function()

        var logger = LoggerImpl()
        var loggerSet = LoggersSet(setOf(logger), calcFunctions = true)

        assertNull(logger.calledMethod)

        loggerSet.log(level, tag, function)

        // Function must be calculated so MESSAGE method will called
        assertEquals(Method.MESSAGE, logger.calledMethod)
        assertEquals(level, logger.level)
        assertEquals(tag, logger.tag)
        assertEquals(functionResult, logger.message)
        assertNull(logger.exception)
        assertNull(logger.function)

        logger = LoggerImpl()
        loggerSet = LoggersSet(setOf(logger), calcFunctions = false)

        assertNull(logger.calledMethod)

        loggerSet.log(level, tag, function)

        assertEquals(Method.FUNCTION, logger.calledMethod)
        assertEquals(level, logger.level)
        assertEquals(tag, logger.tag)
        assertEquals(function, logger.function)
        assertNull(logger.message)
        assertNull(logger.exception)
    }

    private enum class Method {
        MESSAGE,
        EXCEPTION,
        FUNCTION
    }

    private class LoggerImpl: Logger {

        val calledMethod: Method?
            get() { return _calledMethod }

        val level: LogLevel?
            get() { return _level }
        val tag: String?
            get() { return _tag }

        val message: String?
            get() { return _message }
        val exception: Throwable?
            get() { return _exception }
        val function: (() -> String)?
            get() { return _function }

        private var _calledMethod: Method? = null
        private var _level: LogLevel? = null
        private var _tag: String? = null

        private var _message: String? = null
        private var _exception: Throwable? = null
        private var _function: (() -> String)? = null

        override fun log(level: LogLevel, tag: String?, message: String) {
            _calledMethod = Method.MESSAGE
            _level = level
            _tag = tag
            _message = message
        }

        override fun log(level: LogLevel, tag: String?, exception: Throwable) {
            _calledMethod = Method.EXCEPTION
            _level = level
            _tag = tag
            _exception = exception
        }

        override fun log(level: LogLevel, tag: String?, function: () -> String) {
            _calledMethod = Method.FUNCTION
            _level = level
            _tag = tag
            _function = function
        }

    }
}