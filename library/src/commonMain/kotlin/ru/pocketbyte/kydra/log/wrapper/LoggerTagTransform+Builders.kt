package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.Logger


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
@Deprecated(
    "Use withTag(prefix, default, postfix) instead.",
    ReplaceWith("withTag(default = defaultTag)"),
    level = DeprecationLevel.ERROR
)
fun <T : Logger> T.withTag(defaultTag: String): LoggerTagTransform<T> {
    return LoggerTagTransform(this) { it ?: defaultTag }
}

/**
 * Wraps Logger with Logger that transforms tags by applying an optional prefix, default value,
 * and/or postfix.
 *
 * If the tag is null and [default] is also null, the tag remains null. Otherwise, the resolved tag
 * is assembled as `"$prefix$tag$postfix"`, omitting any component that is null.
 *
 * @param prefix String prepended to the tag. Null means no prefix.
 * @param default Fallback tag used when the provided tag is null. Null means tags are not replaced.
 * @param postfix String appended to the tag. Null means no postfix.
 */
fun <T : Logger> T.withTag(
    prefix: String? = null,
    default: String? = null,
    postfix: String? = null
): LoggerTagTransform<T> {
    return LoggerTagTransform(this) { tag ->
        val tag = tag ?: default ?: return@LoggerTagTransform null

        if (prefix != null || postfix != null) {
            StringBuilder().apply {
                prefix?.let { append(it) }
                append(tag)
                postfix?.let { append(it) }
            }.toString()
        } else {
            tag
        }
    }
}
