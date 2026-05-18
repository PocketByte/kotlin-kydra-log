/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

expect object DefaultLoggerFactory {
    @Deprecated(
        message = "Use create() instead.",
        replaceWith = ReplaceWith("create()"),
        level = DeprecationLevel.ERROR
    )
    fun build(): Logger

    fun create(): Logger
}