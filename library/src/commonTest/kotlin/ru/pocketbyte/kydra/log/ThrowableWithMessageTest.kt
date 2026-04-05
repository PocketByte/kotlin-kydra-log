/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ThrowableWithMessageTest {

    @Test
    fun testWithMessageInfix() {
        val exception = RuntimeException("original")
        val message = "Custom context"

        val entry = exception withMessage message

        assertIs<ThrowableWithMessage>(entry)
        assertEquals(message, entry.message)
        assertEquals(exception, entry.throwable)
    }

    @Test
    fun testToString() {
        val exception = RuntimeException("original")
        val message = "Custom context"

        val entry = exception withMessage message
        val result = entry.toString()

        assertTrue(result.startsWith("$message\n"))
        assertTrue(result.contains(exception.stackTraceToString()))
    }

    @Test
    fun testLoggerReceivesEntry() {
        val logger = TestLogger()
        val exception = RuntimeException("boom")
        val message = "Failed to process"

        logger.error { exception withMessage message }

        assertIs<ThrowableWithMessage>(logger.message)
        val entry = logger.message as ThrowableWithMessage
        assertEquals(message, entry.message)
        assertEquals(exception, entry.throwable)
        assertEquals(LogLevel.ERROR, logger.level)
    }

    @Test
    fun testLoggerReceivesEntryWithTag() {
        val logger = TestLogger()
        val exception = RuntimeException("boom")
        val message = "Failed to process"
        val tag = "MyTag"

        logger.warn(tag) { exception withMessage message }

        assertIs<ThrowableWithMessage>(logger.message)
        assertEquals(tag, logger.tag)
        assertEquals(LogLevel.WARNING, logger.level)
    }
}
