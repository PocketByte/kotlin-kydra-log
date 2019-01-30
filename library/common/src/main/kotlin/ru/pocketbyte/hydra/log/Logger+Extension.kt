/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

/**
 * Writes log with provided level and empty tag.
 * @param level Log level
 * @param message Message to be written into log
 * @param arguments List of arguments
 */
fun Logger.log(level: LogLevel, message: String, vararg arguments: Any) {
    log(level, null, message, *arguments)
}

/**
 * Writes exception log with provided level and empty tag.
 * @param level Log level
 * @param exception Exception to be written into log
 */
fun Logger.log(level: LogLevel, exception: Throwable) {
    log(level, null, exception)
}

/**
 * Writes log with provided level and empty tag.
 * @param level Log level
 * @param function Function that returns message to be written into log
 */
fun Logger.log(level: LogLevel, function: () -> String) {
    log(level, null, function)
}

//================================================================
//== LogLevel.INFO ===============================================
/**
 * Writes log with INFO log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 * @param arguments List of arguments
 */
fun Logger.info(tag: String?, message: String, vararg arguments: Any) {
    log(LogLevel.INFO, tag, message, *arguments)
}

/**
 * Writes log with INFO log level and empty tag.
 * @param message Message to be written into log.
 * @param arguments List of arguments
 */
fun Logger.info(message: String, vararg arguments: Any) {
    log(LogLevel.INFO, null, message, *arguments)
}

/**
 * Log exception with INFO log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param exception Exception to be written into log.
 */
fun Logger.info(tag: String?, exception: Throwable) {
    log(LogLevel.INFO, tag, exception)
}

/**
 * Log exception with INFO log level and empty tag.
 * @param exception Exception to be written into log.
 */
fun Logger.info(exception: Throwable) {
    log(LogLevel.INFO, null, exception)
}

/**
 * Writes log with INFO log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
fun Logger.info(tag: String?, function: () -> String) {
    log(LogLevel.INFO, tag, function)
}

/**
 * Writes log with INFO log level and empty tag.
 * @param function Function that returns message to be written into log
 */
fun Logger.info(function: () -> String) {
    log(LogLevel.INFO, null, function)
}

//================================================================
//== LogLevel.DEBUG ==============================================
/**
 * Writes log with DEBUG log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 * @param arguments List of arguments
 */
fun Logger.debug(tag: String?, message: String, vararg arguments: Any) {
    log(LogLevel.DEBUG, tag, message, *arguments)
}

/**
 * Writes log with DEBUG log level and empty tag.
 * @param message Message to be written into log.
 * @param arguments List of arguments
 */
fun Logger.debug(message: String, vararg arguments: Any) {
    log(LogLevel.DEBUG, null, message, *arguments)
}

/**
 * Log exception with DEBUG log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param exception Exception to be written into log.
 */
fun Logger.debug(tag: String?, exception: Throwable) {
    log(LogLevel.DEBUG, tag, exception)
}

/**
 * Log exception with DEBUG log level and empty tag.
 * @param exception Exception to be written into log.
 */
fun Logger.debug(exception: Throwable) {
    log(LogLevel.DEBUG, null, exception)
}

/**
 * Writes log with DEBUG log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
fun Logger.debug(tag: String?, function: () -> String) {
    log(LogLevel.DEBUG, tag, function)
}

/**
 * Writes log with DEBUG log level and empty tag.
 * @param function Function that returns message to be written into log
 */
fun Logger.debug(function: () -> String) {
    log(LogLevel.DEBUG, null, function)
}

//================================================================
//== LogLevel.WARNING ============================================
/**
 * Writes log with WARNING log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 * @param arguments List of arguments
 */
fun Logger.warn(tag: String?, message: String, vararg arguments: Any) {
    log(LogLevel.WARNING, tag, message, *arguments)
}

/**
 * Writes log with WARNING log level and empty tag.
 * @param message Message to be written into log.
 * @param arguments List of arguments
 */
fun Logger.warn(message: String, vararg arguments: Any) {
    log(LogLevel.WARNING, null, message, *arguments)
}

/**
 * Log exception with WARNING log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param exception Exception to be written into log.
 */
fun Logger.warn(tag: String?, exception: Throwable) {
    log(LogLevel.WARNING, tag, exception)
}

/**
 * Log exception with WARNING log level and empty tag.
 * @param exception Exception to be written into log.
 */
fun Logger.warn(exception: Throwable) {
    log(LogLevel.WARNING, null, exception)
}

/**
 * Writes log with WARNING log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
fun Logger.warn(tag: String?, function: () -> String) {
    log(LogLevel.WARNING, tag, function)
}

/**
 * Writes log with WARNING log level and empty tag.
 * @param function Function that returns message to be written into log
 */
fun Logger.warn(function: () -> String) {
    log(LogLevel.WARNING, null, function)
}

//================================================================
//== LogLevel.ERROR ==============================================
/**
 * Writes log with ERROR log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 * @param arguments List of arguments
 */
fun Logger.error(tag: String?, message: String, vararg arguments: Any) {
    log(LogLevel.ERROR, tag, message, *arguments)
}

/**
 * Writes log with ERROR log level and empty tag.
 * @param message Message to be written into log.
 * @param arguments List of arguments
 */
fun Logger.error(message: String, vararg arguments: Any) {
    log(LogLevel.ERROR, null, message, *arguments)
}

/**
 * Log exception with ERROR log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param exception Exception to be written into log.
 */
fun Logger.error(tag: String?, exception: Throwable) {
    log(LogLevel.ERROR, tag, exception)
}

/**
 * Log exception with ERROR log level and empty tag.
 * @param exception Exception to be written into log.
 */
fun Logger.error(exception: Throwable) {
    log(LogLevel.ERROR, null, exception)
}

/**
 * Writes log with ERROR log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
fun Logger.error(tag: String?, function: () -> String) {
    log(LogLevel.ERROR, tag, function)
}

/**
 * Writes log with ERROR log level and empty tag.
 * @param function Function that returns message to be written into log
 */
fun Logger.error(function: () -> String) {
    log(LogLevel.ERROR, null, function)
}