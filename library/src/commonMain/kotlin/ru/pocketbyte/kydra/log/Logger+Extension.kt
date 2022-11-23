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
fun Logger.log(level: LogLevel, message: Any) {
    log(level, null, message)
}

/**
 * Writes log with provided level and empty tag.
 * @param level Log level
 * @param function Function that returns message to be written into log
 */
fun Logger.log(level: LogLevel, function: () -> Any) {
    log(level, null, function)
}

//================================================================
//== LogLevel.INFO ===============================================
/**
 * Writes log with INFO log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 */
fun Logger.info(tag: String?, message: Any) {
    log(LogLevel.INFO, tag, message)
}

/**
 * Writes log with INFO log level and empty tag.
 * @param message Message to be written into log.
 */
fun Logger.info(message: Any) {
    log(LogLevel.INFO, null, message)
}

/**
 * Writes log with INFO log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
fun Logger.info(tag: String?, function: () -> Any) {
    log(LogLevel.INFO, tag, function)
}

/**
 * Writes log with INFO log level and empty tag.
 * @param function Function that returns message to be written into log
 */
fun Logger.info(function: () -> Any) {
    log(LogLevel.INFO, null, function)
}

//================================================================
//== LogLevel.DEBUG ==============================================
/**
 * Writes log with DEBUG log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 */
fun Logger.debug(tag: String?, message: Any) {
    log(LogLevel.DEBUG, tag, message)
}

/**
 * Writes log with DEBUG log level and empty tag.
 * @param message Message to be written into log.
 */
fun Logger.debug(message: Any) {
    log(LogLevel.DEBUG, null, message)
}

/**
 * Writes log with DEBUG log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
fun Logger.debug(tag: String?, function: () -> Any) {
    log(LogLevel.DEBUG, tag, function)
}

/**
 * Writes log with DEBUG log level and empty tag.
 * @param function Function that returns message to be written into log
 */
fun Logger.debug(function: () -> Any) {
    log(LogLevel.DEBUG, null, function)
}

//================================================================
//== LogLevel.WARNING ============================================
/**
 * Writes log with WARNING log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 */
fun Logger.warn(tag: String?, message: Any) {
    log(LogLevel.WARNING, tag, message)
}

/**
 * Writes log with WARNING log level and empty tag.
 * @param message Message to be written into log.
 */
fun Logger.warn(message: Any) {
    log(LogLevel.WARNING, null, message)
}

/**
 * Writes log with WARNING log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
fun Logger.warn(tag: String?, function: () -> Any) {
    log(LogLevel.WARNING, tag, function)
}

/**
 * Writes log with WARNING log level and empty tag.
 * @param function Function that returns message to be written into log
 */
fun Logger.warn(function: () -> Any) {
    log(LogLevel.WARNING, null, function)
}

//================================================================
//== LogLevel.ERROR ==============================================
/**
 * Writes log with ERROR log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param message Message to be written into log.
 */
fun Logger.error(tag: String?, message: Any) {
    log(LogLevel.ERROR, tag, message)
}

/**
 * Writes log with ERROR log level and empty tag.
 * @param message Message to be written into log.
 */
fun Logger.error(message: Any) {
    log(LogLevel.ERROR, null, message)
}

/**
 * Writes log with ERROR log level and provided tag.
 * @param tag Tag of the log record. Nullable
 * @param function Function that returns message to be written into log
 */
fun Logger.error(tag: String?, function: () -> Any) {
    log(LogLevel.ERROR, tag, function)
}

/**
 * Writes log with ERROR log level and empty tag.
 * @param function Function that returns message to be written into log
 */
fun Logger.error(function: () -> Any) {
    log(LogLevel.ERROR, null, function)
}