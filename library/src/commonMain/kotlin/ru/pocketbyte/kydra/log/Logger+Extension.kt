/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Writes a log record with the provided level and a null tag.
 * @param level Log level
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use log(LogLevel, () -> Any) instead.",
    replaceWith = ReplaceWith("log(level) { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.log(level: LogLevel, message: Any) {
    log(level, null) { message }
}

/**
 * Writes a log record with the provided level and a null tag.
 * @param level Log level
 * @param function Function that returns message to be written into log
 */
inline fun Logger.log(level: LogLevel, crossinline function: () -> Any) {
    log(level, null, function)
}

//================================================================
//== LogLevel.INFO ===============================================
/**
 * Writes a log record with INFO level and the provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use info(String?, () -> Any) instead.",
    replaceWith = ReplaceWith("info(tag) { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.info(tag: String?, message: Any) {
    log(LogLevel.INFO, tag) { message }
}

/**
 * Writes a log record with INFO level and a null tag.
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use info(() -> Any) instead.",
    replaceWith = ReplaceWith("info { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.info(message: Any) {
    log(LogLevel.INFO, null) { message }
}

/**
 * Writes a log record with INFO level and the provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
inline fun Logger.info(tag: String?, crossinline function: () -> Any) {
    log(LogLevel.INFO, tag, function)
}

/**
 * Writes a log record with INFO level and a null tag.
 * @param function Function that returns message to be written into log
 */
inline fun Logger.info(crossinline function: () -> Any) {
    log(LogLevel.INFO, null, function)
}

//================================================================
//== LogLevel.DEBUG ==============================================
/**
 * Writes a log record with DEBUG level and the provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use debug(String?, () -> Any) instead.",
    replaceWith = ReplaceWith("debug(tag) { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.debug(tag: String?, message: Any) {
    log(LogLevel.DEBUG, tag) { message }
}

/**
 * Writes a log record with DEBUG level and a null tag.
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use debug(() -> Any) instead.",
    replaceWith = ReplaceWith("debug { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.debug(message: Any) {
    log(LogLevel.DEBUG, null) { message }
}

/**
 * Writes a log record with DEBUG level and the provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
inline fun Logger.debug(tag: String?, crossinline function: () -> Any) {
    log(LogLevel.DEBUG, tag, function)
}

/**
 * Writes a log record with DEBUG level and a null tag.
 * @param function Function that returns message to be written into log
 */
inline fun Logger.debug(crossinline function: () -> Any) {
    log(LogLevel.DEBUG, null, function)
}

//================================================================
//== LogLevel.WARNING ============================================
/**
 * Writes a log record with WARNING level and the provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use warn(String?, () -> Any) instead.",
    replaceWith = ReplaceWith("warn(tag) { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.warn(tag: String?, message: Any) {
    log(LogLevel.WARNING, tag) { message }
}

/**
 * Writes a log record with WARNING level and a null tag.
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use warn(() -> Any) instead.",
    replaceWith = ReplaceWith("warn { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.warn(message: Any) {
    log(LogLevel.WARNING, null) { message }
}

/**
 * Writes a log record with WARNING level and the provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
inline fun Logger.warn(tag: String?, crossinline function: () -> Any) {
    log(LogLevel.WARNING, tag, function)
}

/**
 * Writes a log record with WARNING level and a null tag.
 * @param function Function that returns message to be written into log
 */
inline fun Logger.warn(crossinline function: () -> Any) {
    log(LogLevel.WARNING, null, function)
}

//================================================================
//== LogLevel.ERROR ==============================================
/**
 * Writes a log record with ERROR level and the provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use error(String?, () -> Any) instead.",
    replaceWith = ReplaceWith("error(tag) { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.error(tag: String?, message: Any) {
    log(LogLevel.ERROR, tag) { message }
}

/**
 * Writes a log record with ERROR level and a null tag.
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use error(() -> Any) instead.",
    replaceWith = ReplaceWith("error { message }"),
    level = DeprecationLevel.ERROR
)
fun Logger.error(message: Any) {
    log(LogLevel.ERROR, null) { message }
}

/**
 * Writes a log record with ERROR level and the provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
inline fun Logger.error(tag: String?, crossinline function: () -> Any) {
    log(LogLevel.ERROR, tag, function)
}

/**
 * Writes a log record with ERROR level and a null tag.
 * @param function Function that returns message to be written into log
 */
inline fun Logger.error(crossinline function: () -> Any) {
    log(LogLevel.ERROR, null, function)
}