/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import ru.pocketbyte.kydra.log.print.SimplePrintLogger
import ru.pocketbyte.kydra.log.print.SimplePrinter
import ru.pocketbyte.kydra.log.print.WasmLogMessageFormatter

/**
 * WebAssembly implementation of [Logger] that formats log records using [WasmLogMessageFormatter]
 * and writes them to standard output via [println].
 */
open class WasmLogger : SimplePrintLogger(
    printer = SimplePrinter(),
    logMessageFormatter = WasmLogMessageFormatter()
)
