package ru.pocketbyte.kydra.log.print

class SimplePrinter : Printer {
    override fun print(message: String) {
        println(message)
    }
}