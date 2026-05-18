/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import ru.pocketbyte.kydra.log.print.NativeLogMessageFormatter
import ru.pocketbyte.kydra.log.print.SimplePrintLogger
import ru.pocketbyte.kydra.log.print.SimplePrinter

open class PrintLogger: SimplePrintLogger(
    printer = SimplePrinter(),
    logMessageFormatter = NativeLogMessageFormatter()
)
