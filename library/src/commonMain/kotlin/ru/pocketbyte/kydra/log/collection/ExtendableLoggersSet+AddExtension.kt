package ru.pocketbyte.kydra.log.collection

import ru.pocketbyte.kydra.log.DefaultLogger
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.wrapper.filtered

/**
 * Adds the default platform logger to this set, optionally wrapped in a filter.
 * @param level Minimum log level to pass through. `null` to disable level filtering.
 * @param tags Set of tags to pass through. `null` to disable tag filtering.
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
