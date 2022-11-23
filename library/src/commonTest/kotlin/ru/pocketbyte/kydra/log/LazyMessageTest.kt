/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LazyMessageTest {

    @Test
    fun testLazyMessageCall() {
        var isMessageBuilt = false
        val message = LazyMessage {
            isMessageBuilt = true
            return@LazyMessage "message"
        }

        val logger = LoggerMock()

        assertFalse(isMessageBuilt)

        logger.log(LogLevel.DEBUG, null, message)

        assertTrue(isMessageBuilt)
    }

    @Test
    fun testLazyMessageFilter() {
        var isMessageBuilt = false
        val message = LazyMessage {
            isMessageBuilt = true
            return@LazyMessage "message"
        }

        val logger = LoggerMock().filtered(level = LogLevel.INFO)

        assertFalse(isMessageBuilt)

        logger.log(LogLevel.DEBUG, null, message)

        assertFalse(isMessageBuilt)
    }

    @Test
    fun testLazyMessageMethod() {
        val logger = object : Logger {
            override fun log(level: LogLevel, tag: String?, message: Any) {
                assertTrue(message is LazyMessage)
            }
        }
        logger.log(LogLevel.INFO, null) { "message" }
        logger.log(LogLevel.INFO) { "message" }
        logger.info("tag") { "message" }
        logger.info { "message" }
        logger.error("tag") { "message" }
        logger.error { "message" }
        logger.warn("tag") { "message" }
        logger.warn { "message" }
        logger.debug("tag") { "message" }
        logger.debug { "message" }
    }

    private class LoggerMock: Logger {
        override fun log(level: LogLevel, tag: String?, message: Any) {
            message.toString()
        }
    }
}