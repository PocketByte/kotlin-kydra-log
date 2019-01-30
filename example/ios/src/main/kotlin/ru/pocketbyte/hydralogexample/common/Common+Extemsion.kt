/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydralogexample.common

import ru.pocketbyte.hydra.log.*
import platform.Foundation.*

fun Common.initDefault() {
    HydraLog.initDefault()
}

fun Common.callFun(function: (() -> Unit?)) {
    function()
}
