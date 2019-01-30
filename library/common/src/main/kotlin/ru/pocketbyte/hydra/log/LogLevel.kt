package ru.pocketbyte.hydra.log

/**
 * The Level of Logging.
 *
 * @property priority Priority of the Level.
 */
enum class LogLevel(val priority: Int) {

    /**
     * Debug Log Level. Should be shown only for debugging.
     */
    DEBUG(1),

    /**
     * Information Log Level.
     */
    INFO(2),

    /**
     * Warning Log Level. Should be used to log some warnings.
     */
    WARNING(3),

    /**
     * Error Log Level. Should be used to log some errors.
     */
    ERROR(4)
}