package ru.pocketbyte.hydra.log

/**
 * The Logger interface.
 */
interface Logger {

    /**
     * Writes log with provided level and tag.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param message Message to be written into log
     * @param arguments List of arguments
     */
    fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any)

    /**
     * Writes exception log with provided level and tag.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param exception Exception to be written into log
     */
    fun log(level: LogLevel, tag: String?, exception: Throwable)

    /**
     * Writes log with provided level and tag.
     * @param level Log level
     * @param tag Tag of the log record. Nullable
     * @param function Function that returns message to be written into log
     */
    fun log(level: LogLevel, tag: String?, function: () -> String)

}