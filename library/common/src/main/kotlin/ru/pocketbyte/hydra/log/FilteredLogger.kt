package ru.pocketbyte.hydra.log

/**
 * The Logger implementation that wraps another logger
 * and only passes events that satisfy the filter.
 *
 * @property logger The Logger that should be filtered.
 * @property filter Filter function that defines which logs should be filtered
 *
 * @constructor Creates filtered logger depends on provided filter function.
 */
class FilteredLogger(
        val logger: Logger,
        val filter: (level: LogLevel, tag: String?) -> Boolean
) : Logger {

    /**
     * Creates filtered logger depends on provided log level and set of tags.
     * @param logger The Logger that should be filtered.
     * @param level Minimum log level that can be passed.
     * Null if filter by LogLevel shouldn't be used.
     * @param tags Set of tags that can be passed.
     * Null if filter by Tag shouldn't be used.
     */
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