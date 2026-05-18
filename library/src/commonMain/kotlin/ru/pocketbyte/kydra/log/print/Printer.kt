package ru.pocketbyte.kydra.log.print

/**
 * Writes a formatted log string to an output destination.
 * Implementations define where the output goes (e.g. console, file, or a remote sink).
 */
interface Printer {

    /**
     * Writes [message] to the output destination.
     * @param message The formatted log string to write
     */
    fun print(message: String)
}
