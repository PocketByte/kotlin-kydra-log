/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * Writes log with provided level and empty tag.
 * @param level Log level
 * @param message Message to be written into log
 */
@Deprecated(
    message = "Use log(LogLevel, () -> Any) instead.",
    replaceWith = ReplaceWith("log(level) { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.log(level: LogLevel, message: Any) {
    log(level, null) { message }
}

/**
 * Writes log with provided level and empty tag.
 * @param level Log level
 * @param function Function that returns message to be written into log
 */
inline fun Logger.log(level: LogLevel, function: () -> Any) {
    log(level, null, function)
}

//================================================================
//== LogLevel.INFO ===============================================
/**
 * Writes log with INFO log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 */
@Deprecated(
    message = "Use info(String?, () -> Any) instead.",
    replaceWith = ReplaceWith("info(tag) { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.info(tag: String?, message: Any) {
    log(LogLevel.INFO, tag) { message }
}

/**
 * Writes log with INFO log level and empty tag.
 * @param message Message to be written into log.
 */
@Deprecated(
    message = "Use info(() -> Any) instead.",
    replaceWith = ReplaceWith("info { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.info(message: Any) {
    log(LogLevel.INFO, null) { message }
}

/**
 * Writes log with INFO log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
inline fun Logger.info(tag: String?, function: () -> Any) {
    log(LogLevel.INFO, tag, function)
}

/**
 * Writes log with INFO log level and empty tag.
 * @param function Function that returns message to be written into log
 */
inline fun Logger.info(function: () -> Any) {
    log(LogLevel.INFO, null, function)
}

//================================================================
//== LogLevel.DEBUG ==============================================
/**
 * Writes log with DEBUG log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 */
@Deprecated(
    message = "Use debug(String?, () -> Any) instead.",
    replaceWith = ReplaceWith("debug(tag) { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.debug(tag: String?, message: Any) {
    log(LogLevel.DEBUG, tag) { message }
}

/**
 * Writes log with DEBUG log level and empty tag.
 * @param message Message to be written into log.
 */
@Deprecated(
    message = "Use debug(() -> Any) instead.",
    replaceWith = ReplaceWith("debug { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.debug(message: Any) {
    log(LogLevel.DEBUG, null) { message }
}

/**
 * Writes log with DEBUG log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
inline fun Logger.debug(tag: String?, function: () -> Any) {
    log(LogLevel.DEBUG, tag, function)
}

/**
 * Writes log with DEBUG log level and empty tag.
 * @param function Function that returns message to be written into log
 */
inline fun Logger.debug(function: () -> Any) {
    log(LogLevel.DEBUG, null, function)
}

//================================================================
//== LogLevel.WARNING ============================================
/**
 * Writes log with WARNING log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 */
@Deprecated(
    message = "Use warn(String?, () -> Any) instead.",
    replaceWith = ReplaceWith("warn(tag) { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.warn(tag: String?, message: Any) {
    log(LogLevel.WARNING, tag) { message }
}

/**
 * Writes log with WARNING log level and empty tag.
 * @param message Message to be written into log.
 */
@Deprecated(
    message = "Use warn(() -> Any) instead.",
    replaceWith = ReplaceWith("warn { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.warn(message: Any) {
    log(LogLevel.WARNING, null) { message }
}

/**
 * Writes log with WARNING log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
inline fun Logger.warn(tag: String?, function: () -> Any) {
    log(LogLevel.WARNING, tag, function)
}

/**
 * Writes log with WARNING log level and empty tag.
 * @param function Function that returns message to be written into log
 */
inline fun Logger.warn(function: () -> Any) {
    log(LogLevel.WARNING, null, function)
}

//================================================================
//== LogLevel.ERROR ==============================================
/**
 * Writes log with ERROR log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 */
@Deprecated(
    message = "Use error(String?, () -> Any) instead.",
    replaceWith = ReplaceWith("error(tag) { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.error(tag: String?, message: Any) {
    log(LogLevel.ERROR, tag) { message }
}

/**
 * Writes log with ERROR log level and empty tag.
 * @param message Message to be written into log.
 */
@Deprecated(
    message = "Use error(() -> Any) instead.",
    replaceWith = ReplaceWith("error { message }"),
    level = DeprecationLevel.WARNING
)
fun Logger.error(message: Any) {
    log(LogLevel.ERROR, null) { message }
}

/**
 * Writes log with ERROR log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
inline fun Logger.error(tag: String?, function: () -> Any) {
    log(LogLevel.ERROR, tag, function)
}

/**
 * Writes log with ERROR log level and empty tag.
 * @param function Function that returns message to be written into log
 */
inline fun Logger.error(function: () -> Any) {
    log(LogLevel.ERROR, null, function)
}