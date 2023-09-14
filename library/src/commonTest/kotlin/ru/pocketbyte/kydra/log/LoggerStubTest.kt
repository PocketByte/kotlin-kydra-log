/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import kotlin.test.Test

class LoggerStubTest {

    @Test
    fun testFunction() {
        val logger = LoggerStub()

        logger.log(LogLevel.ERROR) {
            throw RuntimeException("LoggerStub should do anything. Even function call.")
        }
    }
}