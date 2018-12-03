package ru.pocketbyte.hydra.log

expect object HydraLog: Logger {

    /**
     * Init Hydra Logger instance with provided Logger
     * @param logger Logger that should be user for logging
     */
    fun init(logger: Logger)

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any)

    override fun log(level: LogLevel, tag: String?, exception: Throwable)

    override fun log(level: LogLevel, tag: String?, function: () -> String)

}