package ru.pocketbyte.kydra.log.print

import java.io.BufferedWriter
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.io.path.name

/**
 * A [Printer] implementation that writes log records to rotating files in a directory.
 *
 * A new log file is created when the current file exceeds [maxSizeBytes] or when the date changes.
 * The oldest files are deleted when the total directory size approaches [maxFolderSizeBytes].
 * All writes are thread-safe.
 */
class JvmFilePrinter internal constructor(
    private val maxSizeBytes: Long,
    private val maxFolderSizeBytes: Long,
    private val logDirectory: Path,
    private val fsOperator: FileSystemOperator,
    private val dateProvider: () -> Date,
) : Printer {

    /**
     * Creates a file printer that writes to rotating log files in [logDirectory].
     * @param maxSizeBytes Maximum size of a single log file in bytes before rotation occurs
     * @param maxFolderSizeBytes Maximum total size of all log files in bytes before old files are deleted
     * @param logDirectory Path to the directory where log files are stored
     */
    constructor(
        maxSizeBytes: Long,
        maxFolderSizeBytes: Long,
        logDirectory: Path,
    ) : this(maxSizeBytes, maxFolderSizeBytes, logDirectory, NioFileSystemOperator(), { Date() })

    private val lock = ReentrantLock()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val timeFormat = SimpleDateFormat("HH-mm-ss")
    private var currentLogDate: String = getCurrentDate()
    private var currentLogPath: Path = getInitialLogPath()
    private var writer: BufferedWriter? = null

    private fun getCurrentDate(): String = dateFormat.format(dateProvider())

    private fun getCurrentTime(): String = timeFormat.format(dateProvider())

    private fun getInitialLogPath(): Path {
        val logFiles = getSortedLogFiles()
        return if (logFiles.isNotEmpty() && logFiles.last().name.contains(currentLogDate)) {
            logFiles.last()
        } else {
            getCurrentLogPath()
        }
    }

    private fun getCurrentLogPath(): Path = logDirectory.resolve(
        "$LOG_FILE_NAME_PREFIX-${currentLogDate}_${getCurrentTime()}.log"
    )

    init {
        if (fsOperator.exists(logDirectory).not()) {
            fsOperator.createDirectories(logDirectory)
        }
        Runtime.getRuntime().addShutdownHook(Thread { writer?.close() })
    }

    /**
     * Writes [message] to the current log file, rotating the file if needed.
     * This method is thread-safe. Errors during writing are printed to stderr
     * and the current writer is closed and reset.
     * @param message The formatted log string to write
     */
    override fun print(message: String) {
        lock.withLock {
            try {
                rotateFileIfNeeded()
                if (writer == null) {
                    writer = createWriter()
                    checkAndCleanDirectory()
                }
                writer?.apply {
                    write("${message}\n")
                    flush()
                }
            } catch (e: Exception) {
                System.err.println("File print log error: ${e.message}")
                writer?.close()
                writer = null
            }
        }
    }

    private fun createWriter(): BufferedWriter {
        if (fsOperator.exists(currentLogPath).not()) {
            fsOperator.createFile(currentLogPath)
        }
        return fsOperator.newBufferedWriter(
            currentLogPath,
            setOf(StandardOpenOption.CREATE, StandardOpenOption.APPEND)
        )
    }

    private fun rotateFileIfNeeded() {
        val today = getCurrentDate()
        if (today != currentLogDate) {
            currentLogDate = today
            rotateFile()
        }
        val exists = fsOperator.exists(currentLogPath)
        if (exists && fsOperator.size(currentLogPath) >= maxSizeBytes) {
            rotateFile()
        }
        if (exists.not()) {
            writer?.close()
            writer = null
        }
    }

    private fun rotateFile() {
        writer?.close()
        writer = null
        currentLogPath = getCurrentLogPath()
    }

    private fun checkAndCleanDirectory() {
        val logFiles = getSortedLogFiles()
        var totalSize = logFiles.sumOf { fsOperator.size(it) }
        val maxFolderSize = maxFolderSizeBytes - maxSizeBytes

        while (totalSize >= maxFolderSize && logFiles.size > 1) {
            val oldest = logFiles.first()
            totalSize -= fsOperator.size(oldest)
            fsOperator.deleteIfExists(oldest)
            logFiles.removeFirst()
        }
    }

    private fun getSortedLogFiles(): MutableList<Path> {
        return fsOperator.listFiles(logDirectory)
            .sortedBy { fsOperator.getLastModifiedTime(it) }
            .toMutableList()
    }

    companion object {
        private const val LOG_FILE_NAME_PREFIX = "logs"
    }
}
