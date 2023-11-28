/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

class TestLogger : Logger() {

    var level: LogLevel? = null
        private set
    var tag: String? = null
        private set
    var message: Any? = null
        private set

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        this.level = level
        this.tag = tag
        this.message = message
    }
}
