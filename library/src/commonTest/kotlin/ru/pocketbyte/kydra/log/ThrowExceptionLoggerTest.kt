package ru.pocketbyte.kydra.log

import kotlin.test.*

abstract class ThrowExceptionLoggerTest {

    @Test
    fun testConstantExceptionMessage() {
        val exception = RuntimeException()
        val logger = ThrowExceptionLogger(exception)

        try {
            logger.log(LogLevel.ERROR, null, "Hello message")
        } catch (e: Exception) {
            assertSame(exception, e)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testConstantExceptionFunction() {
        val exception = RuntimeException()
        val logger = ThrowExceptionLogger(exception)

        try {
            logger.log(LogLevel.ERROR, null) { "Hello function" }
        } catch (e: Exception) {
            assertSame(exception, e)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testConstantExceptionException() {
        val exception = RuntimeException()
        val logger = ThrowExceptionLogger(exception)

        try {
            logger.log(LogLevel.ERROR, null, IllegalStateException())
        } catch (e: Exception) {
            assertSame(exception, e)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testConstantMessageExceptionMessage() {
        val message = "Some exception message 1"
        val logger = ThrowExceptionLogger(message)


        try {
            logger.log(LogLevel.ERROR, null, "Hello message")
        } catch (e: Exception) {
            assertEquals(message, e.message)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testConstantMessageExceptionFunction() {
        val message = "Some exception message 2"
        val logger = ThrowExceptionLogger(message)

        try {
            logger.log(LogLevel.ERROR, null) { "Hello function" }
        } catch (e: Exception) {
            assertEquals(message, e.message)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testConstantMessageExceptionException() {
        val message = "Some exception message 3"
        val logger = ThrowExceptionLogger(message)

        try {
            logger.log(LogLevel.ERROR, null, IllegalStateException())
        } catch (e: Exception) {
            assertEquals(message, e.message)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testDefaultFactoryMessage() {
        testDefaultFactoryMessage(LogLevel.INFO, "???", "Hello message")
        testDefaultFactoryMessage(LogLevel.DEBUG, "AnotherTag", "Another message")
        testDefaultFactoryMessage(LogLevel.WARNING, "www", "Hello Kydra")
        testDefaultFactoryMessage(LogLevel.ERROR, null, "msg")
    }

    private fun testDefaultFactoryMessage(level: LogLevel, tag: String?, message: String) {
        val logger = ThrowExceptionLogger()
        try {
            logger.log(level, tag, message)
        } catch (e: ThrowExceptionLogger.Exception) {
            assertEquals(level, e.logLevel)
            assertEquals(tag, e.logTag)
            assertEquals(message, e.logMessage)
            assertNull(e.cause)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testDefaultFactoryFunction() {
        testDefaultFactoryFunction(LogLevel.INFO, "AnotherTag") { "Another message" }
        testDefaultFactoryFunction(LogLevel.DEBUG, "???") { "Hello message" }
        testDefaultFactoryFunction(LogLevel.WARNING, null) { "msg" }
        testDefaultFactoryFunction(LogLevel.ERROR, "www") { "Hello Kydra" }
    }

    private fun testDefaultFactoryFunction(level: LogLevel, tag: String?, function: () -> String) {
        val logger = ThrowExceptionLogger()
        try {
            logger.log(level, tag, function)
        } catch (e: ThrowExceptionLogger.Exception) {
            assertEquals(level, e.logLevel)
            assertEquals(tag, e.logTag)
            assertEquals(function(), e.logMessage)
            assertNull(e.cause)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testDefaultFactoryException() {
        testDefaultFactoryException(LogLevel.INFO, null, RuntimeException())
        testDefaultFactoryException(LogLevel.DEBUG, "???", RuntimeException())
        testDefaultFactoryException(LogLevel.WARNING, null, IllegalStateException())
        testDefaultFactoryException(LogLevel.ERROR, "www", IllegalArgumentException())
    }

    private fun testDefaultFactoryException(level: LogLevel, tag: String?, exception: Throwable) {
        val logger = ThrowExceptionLogger()
        try {
            logger.log(level, tag, exception)
        } catch (e: ThrowExceptionLogger.Exception) {
            assertEquals(level, e.logLevel)
            assertEquals(tag, e.logTag)
            assertSame(exception, e.cause)
            assertNull(e.logMessage)
            return
        }
        throw RuntimeException("Exception wasn't thrown")
    }

    @Test
    fun testCustomFactoryException() {
        val messageException = RuntimeException("Message")
        val functionException = RuntimeException("Function")
        val exceptionException = RuntimeException("Exception")
        val logger = ThrowExceptionLogger(object : ThrowExceptionLogger.Factory {
            override fun exceptionFromMessage(
                    level: LogLevel, tag: String?, message: String
            ): Throwable {
                return messageException
            }

            override fun exceptionFromException(
                    level: LogLevel, tag: String?, exception: Throwable
            ): Throwable {
                return exceptionException
            }

            override fun exceptionFromFunction(
                    level: LogLevel, tag: String?, function: () -> String
            ): Throwable {
                return functionException
            }
        })

        var exception: Throwable? = null
        try {
            logger.log(LogLevel.DEBUG, "?/Ta", "MSG")
        } catch (e: Throwable) {
            exception = e
        }
        assertSame<Throwable?>(messageException, exception)

        exception = null
        try {
            logger.log(LogLevel.INFO, null) { "fun" }
        } catch (e: Throwable) {
            exception = e
        }
        assertSame<Throwable?>(functionException, exception)

        exception = null
        try {
            logger.log(LogLevel.WARNING, "Sys Error", RuntimeException())
        } catch (e: Throwable) {
            exception = e
        }
        assertSame<Throwable?>(exceptionException, exception)
    }
}