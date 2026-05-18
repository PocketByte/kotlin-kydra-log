package ru.pocketbyte.kydra.log.wrapper

import ru.pocketbyte.kydra.log.Logger


/**
 * Wraps this logger in a [LoggerTagTransform] that applies [tagTransform] to the tag
 * of every log record.
 *
 * @param tagTransform Function that receives the original tag and returns the transformed tag.
 * May return `null` to clear the tag.
 */
fun <T : Logger> T.withTagTransform(
    tagTransform: (tag: String?) -> String?
): LoggerTagTransform<T> {
    return LoggerTagTransform(this, tagTransform)
}

/**
 * Wraps this logger in a [LoggerTagTransform] that substitutes [defaultTag] when the tag is null.
 *
 * @param defaultTag Tag to use when the log record's tag is null.
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
 * Wraps this logger in a [LoggerTagTransform] that transforms tags by applying an optional
 * prefix, default value, and/or postfix.
 *
 * If the tag is null and [default] is also null, the tag remains null. Otherwise, the resolved tag
 * is assembled as `"$prefix$tag$postfix"`, omitting any component that is null.
 *
 * @param prefix String prepended to the tag. `null` means no prefix.
 * @param default Fallback tag used when the log record's tag is null. `null` means no substitution.
 * @param postfix String appended to the tag. `null` means no postfix.
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
