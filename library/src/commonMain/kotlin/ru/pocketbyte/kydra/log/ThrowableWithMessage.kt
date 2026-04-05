/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

data class ThrowableWithMessage(val message: String, val throwable: Throwable) {
    override fun toString(): String = "$message\n${throwable.stackTraceToString()}"
}

infix fun Throwable.withMessage(message: String): ThrowableWithMessage = ThrowableWithMessage(message, this)
