/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotSame
import kotlin.test.assertSame

abstract class InitializableLoggerTest {

    @Test
    fun testInitialization() {
        val logger1 = LoggerMock()
        val logger2 = LoggerMock()

        assertNotSame(logger1, logger2)

        val initializable1 = InitializableLoggerImpl()
        initializable1.init(logger1)

        assertSame(logger1, initializable1.logger)

        val initializable2 = InitializableLoggerImpl()
        initializable2.init(logger2)

        assertSame(logger2, initializable2.logger)

        val initializable3 = InitializableLoggerImpl()
        initializable3.init(initializable1)

        assertSame(initializable1, initializable3.logger)
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
    fun testNoInitialization() {
        val initializable = object : InitializableLogger() {
            override fun onNeedToBeInitialized() {
                // Do nothing
            }
        }

        assertFailsWith(IllegalStateException::class) {
            initializable.logger
        }
    }


    @Test
    fun testAutoInitialization() {
        val logger = LoggerMock()
        val initializable = object : InitializableLogger() {
            override fun onNeedToBeInitialized() {
                init(logger)
            }
        }

        assertSame(logger, initializable.logger)
    }

    private class InitializableLoggerImpl: InitializableLogger() {
        override fun onNeedToBeInitialized() {
            throw RuntimeException()
        }
    }

    private class LoggerMock: Logger {
        override fun log(level: LogLevel, tag: String?, message: String) {
            throw RuntimeException()
        }

        override fun log(level: LogLevel, tag: String?, exception: Throwable) {
            throw RuntimeException()
        }
    }
}