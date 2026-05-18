/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

/**
 * A no-op logger that silently discards all log records.
 * Because [filter] always returns `false`, message-producing lambdas passed to [Logger.log]
 * are never evaluated.
 */
class LoggerStub: Logger() {

    /**
     * Always returns `false`, causing all log records to be discarded
     * before [doLog] is called.
     */
    override val filter: ((level: LogLevel, tag: String?) -> Boolean) = { _, _ -> false }

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        // Do nothing
    }
}