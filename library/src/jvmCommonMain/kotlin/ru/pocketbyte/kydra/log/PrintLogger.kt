/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydra.log

import ru.pocketbyte.kydra.log.print.JvmLogMessageFormatter
import ru.pocketbyte.kydra.log.print.SimplePrintLogger
import ru.pocketbyte.kydra.log.print.SimplePrinter

/**
 * JVM implementation of Logger that writes logs using println.
 */
open class PrintLogger: SimplePrintLogger(SimplePrinter(), JvmLogMessageFormatter())
