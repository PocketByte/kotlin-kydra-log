package ru.pocketbyte.hydra.log

enum class LogLevel(val priority: Int) {
    DEBUG(1),
    INFO(2),
    WARNING(3),
    ERROR(4)
}