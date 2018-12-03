package ru.pocketbyte.hydra.log

/**
 * Base Logger implementation with filtering by level and tags
 *
 * @author Denis Shurygin
 */
abstract class AbsFilteredLogger(
        val level: LogLevel? = null,
        val tags: Set<String?>? = null,
        val calculateFunctions: Boolean = false
) : Logger {

    abstract fun printLog(level: LogLevel, tag: String?, message: String, vararg arguments: Any)

    abstract fun printLog(level: LogLevel, tag: String?, exception: Throwable)

    abstract fun printLog(level: LogLevel, tag: String?, function: () -> String)

    final override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        if (isSatisfies(level, tag)) {
            printLog(level, tag, message, *arguments)
        }
    }

    final override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        if (isSatisfies(level, tag)) {
            printLog(level, tag, exception)
        }
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        if (isSatisfies(level, tag)) {
            if (calculateFunctions)
                printLog(level, tag, function())
            else
                printLog(level, tag, function)
        }
    }

    internal fun isSatisfies(level: LogLevel, tag: String?): Boolean {
        return (this.level == null || level.priority >= this.level.priority)
                && (this.tags == null || this.tags.contains(tag))
    }
}