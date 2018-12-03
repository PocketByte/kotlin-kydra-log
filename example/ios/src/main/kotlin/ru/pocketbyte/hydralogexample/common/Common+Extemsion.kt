package ru.pocketbyte.hydralogexample.common

import ru.pocketbyte.hydra.log.*
import platform.Foundation.*

fun Common.initDefault() {
    HydraLog.initDefault()
}

fun Common.callFun(function: (() -> Unit?)) {
    function()
}
