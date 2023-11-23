/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import ru.pocketbyte.kydralogexample.common.Common
import kotlinx.browser.document
import kotlinx.browser.window

fun main(args: Array<String>) {
    window.onload = {
        val inputMessage = document.getElementById("message_id") as HTMLInputElement
        val cbStacktrace = document.getElementById("cb_stacktrace_id") as HTMLInputElement

        val buttonInfo = document.getElementById("button_info_id")
        val buttonDebug = document.getElementById("button_debug_id")
        val buttonWarning = document.getElementById("button_warning_id")
        val buttonError = document.getElementById("button_error_id")

        buttonInfo?.addEventListener("click", fun(_: Event) {
            Common.printInfo(inputMessage.value, cbStacktrace.checked)
        })
        buttonDebug?.addEventListener("click", fun(_: Event) {
            Common.printDebug(inputMessage.value, cbStacktrace.checked)
        })
        buttonWarning?.addEventListener("click", fun(_: Event) {
            Common.printWarning(inputMessage.value, cbStacktrace.checked)
        })
        buttonError?.addEventListener("click", fun(_: Event) {
            Common.printError(inputMessage.value, cbStacktrace.checked)
        })
    }
}

