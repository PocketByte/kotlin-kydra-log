/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import ru.pocketbyte.kydra.log.print.JvmLogMessageFormatter
import ru.pocketbyte.kydra.log.print.SimplePrintLogger
import ru.pocketbyte.kydra.log.print.SimplePrinter

/**
 * JVM implementation of [Logger] that formats log records using [JvmLogMessageFormatter]
 * and writes them to standard output via [println].
 */
open class PrintLogger: SimplePrintLogger(
    printer = SimplePrinter(),
    logMessageFormatter = JvmLogMessageFormatter()
)
