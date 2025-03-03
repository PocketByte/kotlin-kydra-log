package ru.pocketbyte.kydra.log.print

import java.io.BufferedWriter
import java.nio.file.CopyOption
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.attribute.FileTime

internal interface FileSystemOperator {
    fun createDirectories(dir: Path): Path
    fun newBufferedWriter(path: Path, options: Set<OpenOption>): BufferedWriter
    fun listFiles(dir: Path): Sequence<Path>
    fun deleteIfExists(path: Path): Boolean
    fun move(source: Path, target: Path, vararg options: CopyOption)
    fun exists(path: Path): Boolean
    fun size(path: Path): Long
    fun getLastModifiedTime(path: Path): FileTime

    fun createFile(path: Path)
}
