package ru.pocketbyte.kydra.log

import ru.pocketbyte.kydra.log.print.JvmFilePrinter
import ru.pocketbyte.kydra.log.print.JvmLogMessageFormatter
import ru.pocketbyte.kydra.log.print.SimplePrintLogger
import java.nio.file.Path

object FileLoggerFactory {

    private const val KB_FACTOR: Long = 1000
    private const val MB_FACTOR: Long = 1000 * KB_FACTOR
    private const val DEFAULT_MAX_FILE_SIZE = 2 * MB_FACTOR
    private const val DEFAULT_MAX_FOLDER_SIZE = 20 * MB_FACTOR

    fun create(
        logDirectoryPath: Path,
        maxSizeBytes: Long = DEFAULT_MAX_FILE_SIZE,
        maxFolderSizeBytes: Long = DEFAULT_MAX_FOLDER_SIZE,
    ): SimplePrintLogger {
        return SimplePrintLogger(
            printer = JvmFilePrinter(
                logDirectory = logDirectoryPath,
                maxSizeBytes = maxSizeBytes,
                maxFolderSizeBytes = maxFolderSizeBytes
            ),
            logMessageFormatter = JvmLogMessageFormatter()
        )
    }
}
