/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

expect object DefaultLoggerFactory {
    @Deprecated("Use 'build()' without parameters. To filter use extension 'filtered()'.")
    fun build(level: LogLevel? = null, tags: Set<String?>? = null): Logger
    fun build(): Logger
}