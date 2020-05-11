/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

open class AbsPrintLoggerTest {

    @Test
    fun testPrintMessage() {
        val logger = AbsPrintLoggerMock()

        assertNull(logger.logMessage)

        logger.log(LogLevel.DEBUG, "Tag", "Legs are tied, these hands are broken")
        assertEquals("D/Tag: Legs are tied, these hands are broken", logger.logMessage)

        logger.log(LogLevel.INFO, "Another Tag", "Alone I try with words unspoken")
        assertEquals("I/Another Tag: Alone I try with words unspoken", logger.logMessage)

        logger.log(LogLevel.WARNING, "OK", "Silent cry, my breath is frozen")
        assertEquals("W/OK: Silent cry, my breath is frozen", logger.logMessage)

        logger.log(LogLevel.ERROR, null, "With blinded eyes, I fear myself!")
        assertEquals("E/: With blinded eyes, I fear myself!", logger.logMessage)
    }

    @Test
    fun testPrintException() {
        val logger = AbsPrintLoggerMock()

        assertNull(logger.logMessage)

        logger.log(LogLevel.DEBUG, "TEST", RuntimeException("It's burning down, it's burning high"))
        assertEquals("D/TEST: <Exception Name>: It's burning down, it's burning high\n" +
                "<Exception StackTrace>", logger.logMessage)

        logger.log(LogLevel.INFO, null, RuntimeException("When ashes fall the legends rise"))
        assertEquals("I/: <Exception Name>: When ashes fall the legends rise\n" +
                "<Exception StackTrace>", logger.logMessage)

        logger.log(LogLevel.WARNING, "We burn it out a mile wide", RuntimeException())
        assertEquals("W/We burn it out a mile wide: <Exception Name>\n" +
                "<Exception StackTrace>", logger.logMessage)

        logger.log(LogLevel.ERROR, null, RuntimeException("When ashes fall, the legends rise"))
        assertEquals("E/: <Exception Name>: When ashes fall, the legends rise\n" +
                "<Exception StackTrace>", logger.logMessage)
    }

    private class AbsPrintLoggerMock: AbsPrintLogger() {

        var logMessage: String? = null

        override fun printLog(message: String) {
            logMessage = message
        }

        override fun stackTrace(exception: Throwable): String {
            return "<Exception StackTrace>"
        }

        override fun qualifiedName(exception: Throwable): String {
            return "<Exception Name>"
        }

    }
}