/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import ru.pocketbyte.hydralogexample.common.Common
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        val input = document.getElementById("message_id") as HTMLInputElement
        val cbStacktrace = document.getElementById("cb_stacktrace_id") as HTMLInputElement

        val buttonInfo = document.getElementById("button_info_id")
        val buttonDebug = document.getElementById("button_debug_id")
        val buttonWarning = document.getElementById("button_warning_id")
        val buttonError = document.getElementById("button_error_id")

        buttonInfo?.addEventListener("click", fun(_: Event) {
            Common.printInfo(input.value, cbStacktrace.checked)
        })
        buttonDebug?.addEventListener("click", fun(_: Event) {
            Common.printDebug(input.value, cbStacktrace.checked)
        })
        buttonWarning?.addEventListener("click", fun(_: Event) {
            Common.printWarning(input.value, cbStacktrace.checked)
        })
        buttonError?.addEventListener("click", fun(_: Event) {
            Common.printError(input.value, cbStacktrace.checked)
        })
    }
}

