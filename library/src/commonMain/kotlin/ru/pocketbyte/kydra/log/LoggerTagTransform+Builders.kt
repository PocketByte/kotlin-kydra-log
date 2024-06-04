package ru.pocketbyte.kydra.log



/**
 * Wraps Logger with Logger that transforms tags by given logic.
 *
 * @param tagTransform Tag transformation logic
 */
fun <T : Logger> T.withTagTransform(
    tagTransform: (tag: String?) -> String?
): LoggerTagTransform<T> {
    return LoggerTagTransform(this, tagTransform)
}

/**
 * Wraps Logger with Logger that uses defaultTag if provided tag is null.
 *
 * @param defaultTag Tag that should be used if provided tag is null.
 */
fun <T : Logger> T.withTag(defaultTag: String): LoggerTagTransform<T> {
    return LoggerTagTransform(this) { it ?: defaultTag }
}
