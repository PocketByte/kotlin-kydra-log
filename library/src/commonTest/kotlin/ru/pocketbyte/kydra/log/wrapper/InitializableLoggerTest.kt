/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.Logger
import ru.pocketbyte.kydra.log.TestLogger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InitializableLoggerTest {

    @Test
    fun testInitialization() {
        val logger = TestLogger()

        val initializable = InitializableLoggerImpl()
        initializable.init(logger)

        val level = LogLevel.INFO
        val tag = "TEST_2"
        val message = "Some test message!"

        initializable.log(level, tag) { message }

        assertEquals(level, logger.level)
        assertEquals(tag, logger.tag)
        assertEquals(message, logger.message)
    }

    @Test
    fun testDoubleInitialization() {
        val initializable = InitializableLoggerImpl()
        initializable.init(LoggerMock())

        assertFailsWith(IllegalStateException::class) {
            initializable.init(LoggerMock())
        }
    }

    @Test
    fun testDoubleInitializationWithSameLogger() {
        val logger = LoggerMock()
        val initializable = InitializableLoggerImpl()
        initializable.init(logger)

        assertFailsWith(IllegalStateException::class) {
            initializable.init(logger)
        }
    }


    @Test
    fun testDefaultLoggerWithNoInitialization() {
        val logger = TestLogger()
        val initializable = object : InitializableLogger<Logger>() {
            override val defaultLogger: Logger = logger
        }


        val level = LogLevel.ERROR
        val tag = "TEST_3"
        val message = "Some message 3!"

        initializable.log(level, tag) { message }

        assertEquals(level, logger.level)
        assertEquals(tag, logger.tag)
        assertEquals(message, logger.message)
    }

    @Test
    fun testInitializationFlag() {
        val logger = InitializableLoggerImpl()

        assertFalse(logger.isInitialized)

        logger.init(LoggerMock())

        assertTrue(logger.isInitialized)
    }

    private class InitializableLoggerImpl: InitializableLogger<Logger>() {
        override val defaultLogger: Logger
            get() = throw RuntimeException()
    }

    private class LoggerMock: Logger() {
        override fun doLog(level: LogLevel, tag: String?, message: Any) {
            throw NotImplementedError()
        }
    }
}