package ru.pocketbyte.kydra.log

internal val DefaultLogger: Logger by lazy {
    DefaultLoggerFactory.build()
}