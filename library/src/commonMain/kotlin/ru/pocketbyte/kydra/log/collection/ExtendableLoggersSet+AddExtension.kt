package ru.pocketbyte.kydra.log.collection

import ru.pocketbyte.kydra.log.DefaultLogger
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.wrapper.filtered

/**
 * Add default Logger with provided filters.
 * @param level Minimum log level that can be passed.
 * Null if filter by LogLevel shouldn't be used.
 * @param tags Set of tags that can be passed.
 * Null if filter by Tag shouldn't be used.
 */
fun ExtendableLoggersSet.addDefault(level: LogLevel? = null, tags: Set<String?>? = null) {
    addLogger(
        if (level != null || tags != null) {
            DefaultLogger.filtered(level, tags)
        } else {
            DefaultLogger
        }
    )
}
