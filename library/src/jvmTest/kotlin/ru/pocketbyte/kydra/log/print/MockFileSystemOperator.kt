package ru.pocketbyte.kydra.log.print

import java.io.BufferedWriter
import java.io.Writer
import java.nio.file.CopyOption
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.attribute.FileTime
import java.util.*

class MockFileSystemOperator(
    private val dateProvider: () -> Date
) : FileSystemOperator {
    val files = mutableMapOf<Path, String>()
    private var currentTime = FileTime.fromMillis(dateProvider().time)

    override fun createDirectories(dir: Path) = dir

    override fun newBufferedWriter(path: Path, options: Set<OpenOption>): BufferedWriter {
        return object : BufferedWriter(Writer.nullWriter()) {
            override fun write(str: String) {
                files.merge(path, str) { old, new -> old + new }
            }

            override fun flush() {}
            override fun close() {}
        }
    }

    override fun listFiles(dir: Path) = files.keys.asSequence()

    override fun deleteIfExists(path: Path): Boolean = files.remove(path) != null

    override fun move(source: Path, target: Path, vararg options: CopyOption) {
        files[target] = files.remove(source) ?: ""
    }

    override fun exists(path: Path) = files.containsKey(path)

    override fun size(path: Path): Long {
        return files[path]?.length?.toLong() ?: 0L
    }

    override fun getLastModifiedTime(path: Path): FileTime = currentTime ?: FileTime.from(dateProvider().toInstant())
    override fun createFile(path: Path) {
        files[path] = ""
    }
}
