package ru.pocketbyte.kydra.log

import java.io.BufferedWriter
import java.nio.charset.StandardCharsets
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.attribute.FileTime
import kotlin.streams.asSequence

class NioFileSystemOperator : FileSystemOperator {
    override fun createDirectories(dir: Path): Path = Files.createDirectories(dir)

    override fun newBufferedWriter(path: Path, options: Set<OpenOption>): BufferedWriter =
        Files.newBufferedWriter(path, StandardCharsets.UTF_8, *options.toTypedArray())

    override fun listFiles(dir: Path): Sequence<Path> = Files.list(dir).asSequence()

    override fun deleteIfExists(path: Path): Boolean = Files.deleteIfExists(path)

    override fun move(source: Path, target: Path, vararg options: CopyOption) {
        Files.move(source, target, *options)
    }

    override fun exists(path: Path): Boolean = Files.exists(path)

    override fun size(path: Path): Long = Files.size(path)

    override fun getLastModifiedTime(path: Path): FileTime = Files.getLastModifiedTime(path)

    override fun createFile(path: Path) {
        Files.createFile(path)
    }
}
