package ru.pocketbyte.kydra.log.print

import java.nio.file.Path
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class JvmFilePrinterTest {

    @Test
    fun `Write logs to file`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        val exceptedString = "Test string"
        runWith(
            maxFileLength = 10,
            maxFolderLength = 100,
            mockFs,
        ) {
            print(exceptedString)
        }
        assertEquals(exceptedString + "\n", mockFs.files.values.first())
    }

    @Test
    fun `Rotate files when file size limit exceeded`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        runWith(
            maxFileLength = 10,
            maxFolderLength = 100,
            mockFs,
            dateProvider = dateProvider
        ) {
            repeat(2) {
                print("9 symbols")
                mockDateProvider.plus(1000)
            }
        }
        assertEquals(2, mockFs.files.size)
    }

    @Test
    fun `Remove old files when folder limit exceeded`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        runWith(
            maxFileLength = 10,
            maxFolderLength = 25,
            mockFs,
            dateProvider = dateProvider
        ) {
            repeat(90) {
                this.print("test")
                mockDateProvider.plus(2000)
            }
        }
        assertEquals(2, mockFs.files.size)
    }

    @Test
    fun `Log folder doesn't exceed max size`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        val exceptedSize = 50L
        runWith(
            maxFileLength = 10,
            maxFolderLength = exceptedSize,
            mockFs,
            dateProvider = dateProvider
        ) {
            repeat(90) {
                this.print("test")
                mockDateProvider.plus(2000)
            }
        }
        var actualSize = 0L
        mockFs.files.forEach {
            actualSize += it.value.length
        }
        assertTrue { exceptedSize >= actualSize }
    }

    @Test
    fun `Write to a new file if the date has changed`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        runWith(
            maxFileLength = Long.MAX_VALUE - 100,
            maxFolderLength = Long.MAX_VALUE,
            mockFs,
            dateProvider = dateProvider
        ) {
            this.print("test")
            mockDateProvider.plus(1.toDuration(DurationUnit.DAYS))
            this.print("test")
        }
        assertEquals(2, mockFs.files.size)
    }

    @Test
    fun `New log file for same day has new time postfix`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        runWith(
            maxFileLength = 10,
            maxFolderLength = 50,
            mockFs,
            dateProvider = dateProvider
        ) {
            this.print("String to exceed size limit")
            mockDateProvider.plus(2000)
            this.print("String for second file")
        }
        val exceptedPostfix = SimpleDateFormat("HH-mm-ss").format(dateProvider())
        assertEquals(2, mockFs.files.size)
        assertTrue { mockFs.files.any { it.key.toString().contains(exceptedPostfix) } }
    }

    @Test
    fun `Write in same file if there is new session and same date`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        val exceptedPostfix = SimpleDateFormat("HH-mm-ss").format(dateProvider())
        runWith(
            maxFileLength = 50,
            maxFolderLength = 500,
            mockFs,
            dateProvider = dateProvider
        ) {
            this.print("String")
        }
        mockDateProvider.plus(2000L)
        runWith(
            maxFileLength = 50,
            maxFolderLength = 500,
            mockFs,
            dateProvider = dateProvider
        ) {
            this.print("String")
        }
        assertEquals(1, mockFs.files.size)
        assertTrue { mockFs.files.any { it.key.toString().contains(exceptedPostfix) } }
    }

    @Test
    fun `Always have at least one file in logs`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        runWith(
            maxFileLength = 10,
            maxFolderLength = 0,
            mockFs,
            dateProvider = dateProvider
        ) {
            repeat(90) {
                this.print("test")
                mockDateProvider.plus(2000)
            }
        }
        assertEquals(1, mockFs.files.size)
    }

    @Test
    fun `Create nothing if there is no log calls`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        runWith(
            maxFileLength = 10,
            maxFolderLength = 20,
            mockFs,
            dateProvider = dateProvider
        ) {}
        assertEquals(0, mockFs.files.size)
    }

    @Test
    fun `Create new file if current file was deleted by outer power`() {
        val mockDateProvider = MockDateProvider()
        val dateProvider = { Date.from(Instant.ofEpochMilli(mockDateProvider.currentEpochMilliseconds)) }
        val mockFs = MockFileSystemOperator(dateProvider)
        runWith(
            maxFileLength = 10,
            maxFolderLength = 20,
            mockFs,
            dateProvider = dateProvider
        ) {
            this.print("Test")
            mockFs.files.clear()
            this.print("Test")
        }
        assertEquals(1, mockFs.files.size)
    }

    private fun runWith(
        maxFileLength: Long,
        maxFolderLength: Long,
        mockFs: FileSystemOperator = MockFileSystemOperator { Date() },
        dateProvider: () -> Date = { Date() },
        action: JvmFilePrinter.() -> Unit,
    ) {
        val printer = JvmFilePrinter(
            logDirectory = Path.of("test"),
            maxSizeBytes = maxFileLength,
            maxFolderSizeBytes = maxFolderLength,
            fsOperator = mockFs,
            dateProvider = dateProvider
        )
        printer.run(action)
    }
}
