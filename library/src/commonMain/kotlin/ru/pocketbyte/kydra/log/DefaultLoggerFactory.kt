/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

expect object DefaultLoggerFactory {
    @Deprecated(
        message = "Use 'create()' without parameters. To filter use extension 'filtered()'.",
        replaceWith = ReplaceWith("build().filtered(level, tags)"),
        level = DeprecationLevel.ERROR
    )
    fun build(level: LogLevel? = null, tags: Set<String?>? = null): Logger

    @Deprecated(
        message = "Use create() instead.",
        replaceWith = ReplaceWith("create()"),
        level = DeprecationLevel.WARNING
    )
    fun build(): Logger

    fun create(): Logger
}