package ru.pocketbyte.hydra.log

import kotlin.test.*

abstract class AbsFilteredLoggerTest {

    abstract fun random(): Long

    @Test
    fun testIsSatisfiesAll() {
        val logger = AbsFilteredLoggerImpl()

        LogLevel.values().forEach { level ->
            assertEquals(true, logger.isSatisfies(level, null),
                    "Level ${level.name} not satisfied but should")
        }

        assertEquals(true, logger.isSatisfies(LogLevel.INFO, "TAG"))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, "TEST"))
    }

    @Test
    fun testIsSatisfiesLevels() {
        var logger = AbsFilteredLoggerImpl(level = null)
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, null))
        assertEquals(true, logger.isSatisfies(LogLevel.INFO, null))
        assertEquals(true, logger.isSatisfies(LogLevel.WARNING, null))
        assertEquals(true, logger.isSatisfies(LogLevel.ERROR, null))

        logger = AbsFilteredLoggerImpl(LogLevel.DEBUG)
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, null))
        assertEquals(true, logger.isSatisfies(LogLevel.INFO, null))
        assertEquals(true, logger.isSatisfies(LogLevel.WARNING, null))
        assertEquals(true, logger.isSatisfies(LogLevel.ERROR, null))

        logger = AbsFilteredLoggerImpl(LogLevel.INFO)
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, null))
        assertEquals(true, logger.isSatisfies(LogLevel.INFO, null))
        assertEquals(true, logger.isSatisfies(LogLevel.WARNING, null))
        assertEquals(true, logger.isSatisfies(LogLevel.ERROR, null))

        logger = AbsFilteredLoggerImpl(LogLevel.WARNING)
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, null))
        assertEquals(false, logger.isSatisfies(LogLevel.INFO, null))
        assertEquals(true, logger.isSatisfies(LogLevel.WARNING, null))
        assertEquals(true, logger.isSatisfies(LogLevel.ERROR, null))

        logger = AbsFilteredLoggerImpl(LogLevel.ERROR)
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, null))
        assertEquals(false, logger.isSatisfies(LogLevel.INFO, null))
        assertEquals(false, logger.isSatisfies(LogLevel.WARNING, null))
        assertEquals(true, logger.isSatisfies(LogLevel.ERROR, null))
    }

    @Test
    fun testIsSatisfiesTags() {
        val tag1 = "tag1${random() + 1}"
        val tag2 = "tag2${random() + 2}"
        val tag3 = "tag3${random() + 3}"
        val tag4 = "tag4${random() + 4}"

        var logger = AbsFilteredLoggerImpl(tags = null)
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag4))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, null))

        logger = AbsFilteredLoggerImpl(tags = setOf(tag1, tag2))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag2))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag3))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, null))

        logger = AbsFilteredLoggerImpl(tags = setOf(tag2, tag3))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag1))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag3))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, null))

        logger = AbsFilteredLoggerImpl(tags = setOf(tag3, tag4))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag1))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag2))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag4))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, null))

        logger = AbsFilteredLoggerImpl(tags = setOf(null, tag4))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag1))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag2))
        assertEquals(false, logger.isSatisfies(LogLevel.DEBUG, tag3))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, tag4))
        assertEquals(true, logger.isSatisfies(LogLevel.DEBUG, null))
    }

    @Test
    fun testLevelFilter() {
        LogLevel.values().forEach { expectLevel ->
            val logger = AbsFilteredLoggerImpl(expectLevel)

            assertNull(logger.calledMethod)

            LogLevel.values().forEach { level ->
                logger.reset()
                logger.log(level, null, "message")

                if (expectLevel.priority <= level.priority)
                    assertEquals(Method.MESSAGE, logger.calledMethod,
                            "Level ${level.name} not satisfied " +
                                    "for filtering by ${expectLevel.name} but should")
                else
                    assertNull(logger.calledMethod,
                            "Level ${level.name} satisfied " +
                                    "for filtering by ${expectLevel.name} but shouldn't")

                logger.reset()
                logger.log(level, null, RuntimeException())

                if (expectLevel.priority <= level.priority)
                    assertEquals(Method.EXCEPTION, logger.calledMethod,
                            "Level ${level.name} not satisfied " +
                                    "for filtering by ${expectLevel.name} but should")
                else
                    assertNull(logger.calledMethod,
                            "Level ${level.name} satisfied " +
                                    "for filtering by ${expectLevel.name} but shouldn't")


                logger.reset()
                logger.log(level, null) { "message" }

                if (expectLevel.priority <= level.priority)
                    assertEquals(Method.FUNCTION, logger.calledMethod,
                            "Level ${level.name} not satisfied " +
                                    "for filtering by ${expectLevel.name} but should")
                else
                    assertNull(logger.calledMethod,
                            "Level ${level.name} satisfied " +
                                    "for filtering by ${expectLevel.name} but shouldn't")
            }
        }
    }

    @Test
    fun testTagFilter() {
        val gudTag = "gudTag${random()}"
        val badTag = "badTag${random()}"

        val logger = AbsFilteredLoggerImpl(tags = setOf(gudTag))

        logger.reset()
        logger.log(LogLevel.DEBUG, gudTag, "some message")
        assertEquals(Method.MESSAGE, logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, badTag, "some message")
        assertNull(logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, null, "some message")
        assertNull(logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, gudTag, RuntimeException())
        assertEquals(Method.EXCEPTION, logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, badTag, RuntimeException())
        assertNull(logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, null, RuntimeException())
        assertNull(logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, gudTag) { "message" }
        assertEquals(Method.FUNCTION, logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, badTag) { "message" }
        assertNull(logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, null) { "message" }
        assertNull(logger.calledMethod)
    }

    @Test
    fun testCalculateFunctions() {
        val logger = AbsFilteredLoggerImpl(calculateFunctions = true)

        logger.reset()
        logger.log(LogLevel.DEBUG, null, "some message")
        assertEquals(Method.MESSAGE, logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, null, RuntimeException())
        assertEquals(Method.EXCEPTION, logger.calledMethod)

        logger.reset()
        logger.log(LogLevel.DEBUG, null) { "message" }
        assertEquals(Method.MESSAGE, logger.calledMethod)
    }

    private enum class Method {
        MESSAGE,
        EXCEPTION,
        FUNCTION
    }

    private class AbsFilteredLoggerImpl(
            level: LogLevel? = null,
            tags: Set<String?>? = null,
            calculateFunctions: Boolean = false
    ): AbsFilteredLogger(level, tags, calculateFunctions) {

        val calledMethod: Method?
            get() { return _calledMethod }

        private var _calledMethod: Method? = null

        override fun printLog(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
            _calledMethod = Method.MESSAGE
        }

        override fun printLog(level: LogLevel, tag: String?, exception: Throwable) {
            _calledMethod = Method.EXCEPTION
        }

        override fun printLog(level: LogLevel, tag: String?, function: () -> String) {
            _calledMethod = Method.FUNCTION
        }

        fun reset() {
            _calledMethod = null
        }

    }
}