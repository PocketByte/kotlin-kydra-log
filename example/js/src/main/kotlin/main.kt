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

        val inputMessage2 = document.getElementById("message_2_id") as HTMLInputElement
        val inputArguments2 = document.getElementById("arguments_2_id") as HTMLInputElement

        val buttonPrint = document.getElementById("button_print_id")

        buttonPrint?.addEventListener("click", fun(_: Event) {
            Common.print(inputMessage2.value, inputArguments2.value)
        })
    }
}

