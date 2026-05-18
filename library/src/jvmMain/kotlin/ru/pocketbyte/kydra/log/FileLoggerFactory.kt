package ru.pocketbyte.kydra.log

import ru.pocketbyte.kydra.log.print.JvmFilePrinter
import ru.pocketbyte.kydra.log.print.JvmLogMessageFormatter
import ru.pocketbyte.kydra.log.print.SimplePrintLogger
import java.nio.file.Path

/**
 * Factory that creates a file-based [Logger] for JVM.
 * Log records are formatted using [JvmLogMessageFormatter] and written to rotating log files
 * in the specified directory via [JvmFilePrinter].
 */
object FileLoggerFactory {

    private const val KB_FACTOR: Long = 1000
    private const val MB_FACTOR: Long = 1000 * KB_FACTOR
    private const val DEFAULT_MAX_FILE_SIZE = 2 * MB_FACTOR
    private const val DEFAULT_MAX_FOLDER_SIZE = 20 * MB_FACTOR

    /**
     * Creates a logger that writes to rotating log files in [logDirectoryPath].
     * A new log file is started when the current file exceeds [maxSizeBytes].
     * The oldest files are deleted when the total directory size exceeds [maxFolderSizeBytes].
     * @param logDirectoryPath Path to the directory where log files are stored
     * @param maxSizeBytes Maximum size of a single log file in bytes. Defaults to 2 MB.
     * @param maxFolderSizeBytes Maximum total size of all log files in bytes. Defaults to 20 MB.
     */
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
