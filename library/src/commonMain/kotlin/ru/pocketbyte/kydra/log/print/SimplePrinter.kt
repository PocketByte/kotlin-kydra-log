package ru.pocketbyte.kydra.log.print

/**
 * A [Printer] implementation that writes to standard output via [println].
 */
class SimplePrinter : Printer {
    override fun print(message: String) {
        println(message)
    }
}
