/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydra.log

actual fun HydraLog.initDefault(level: LogLevel?, tags: Set<String?>?) {
    throw IllegalStateException("Default HydraLog not able." +
            " Please call init(logger: Logger) before printing.")
}