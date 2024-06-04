/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

class InitializableLoggerTest {

    @Test
    fun testInitialization() {
        val logger1 = LoggerMock()

        val initializable1 = InitializableLoggerImpl()
        initializable1.init(logger1)

        assertSame(logger1, initializable1.logger)

        val initializable2 = InitializableLoggerImpl()
        initializable2.init(initializable1)

        assertSame(initializable1, initializable2.logger)
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
        val logger = LoggerMock()
        val initializable = object : InitializableLogger<Logger>() {
            override val defaultLogger: Logger = logger
        }

        assertSame(logger, initializable.logger)
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