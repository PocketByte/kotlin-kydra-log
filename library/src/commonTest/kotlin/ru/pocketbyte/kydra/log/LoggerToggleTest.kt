/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class LoggerToggleTest {

    @Test
    fun testWrapper() {
        val logger = TestLogger()
        val loggerToggle = LoggerToggle(logger)

        assertSame(logger, loggerToggle.logger)
    }

    @Test
    fun testDefaultValue() {
        val loggerToggle = LoggerToggle(TestLogger())
        assertTrue(loggerToggle.enabled)
    }

    @Test
    fun testLoggerEnabled() {
        val message = "Hello Logger!"
        val logger = TestLogger()
        val loggerToggle = LoggerToggle(logger)

        assertNull(logger.message)

        loggerToggle.enabled = true
        loggerToggle.info { message }

        assertEquals(message, logger.message)
    }

    @Test
    fun testLoggerDisabled() {
        val logger = object : Logger() {
            override fun doLog(level: LogLevel, tag: String?, message: Any) {
                throw RuntimeException("This method shouldn't be called")
            }
        }
        val loggerToggle = LoggerToggle(logger)

        loggerToggle.enabled = false
        loggerToggle.info { "Hello Logger!" }
    }
}
