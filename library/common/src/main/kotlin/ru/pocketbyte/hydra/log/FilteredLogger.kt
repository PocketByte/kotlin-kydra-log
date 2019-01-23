package ru.pocketbyte.hydra.log

/**
 * @author Shurygin Denis
 */
class FilteredLogger(
        val logger: Logger,
        val filter: (level: LogLevel, tag: String?) -> Boolean
) : Logger {

    constructor(logger: Logger, level: LogLevel? = null, tags: Set<String?>? = null) :
            this(logger, { pLevel: LogLevel, pTag: String? -> Boolean
                (level == null || pLevel.priority >= level.priority)
                        && (tags == null || tags.contains(pTag))
            })

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
        if (filter(level, tag)) {
            logger.log(level, tag, message, *arguments)
        }
    }

    override fun log(level: LogLevel, tag: String?, exception: Throwable) {
        if (filter(level, tag)) {
            logger.log(level, tag, exception)
        }
    }

    override fun log(level: LogLevel, tag: String?, function: () -> String) {
        if (filter(level, tag)) {
            logger.log(level, tag, function)
        }
    }
}