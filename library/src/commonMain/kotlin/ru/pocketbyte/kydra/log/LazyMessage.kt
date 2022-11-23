/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

class LazyMessage(
    val messageBuilder: () -> Any
) {

    private var cachedMessage: Any? = null

    fun getMessage(): Any {
        cachedMessage?.let { return it }

        val message = messageBuilder()
        cachedMessage = message
        return message
    }

    override fun toString(): String {
        return getMessage().toString()
    }

}