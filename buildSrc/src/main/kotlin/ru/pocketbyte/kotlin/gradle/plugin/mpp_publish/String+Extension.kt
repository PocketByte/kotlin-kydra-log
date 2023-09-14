package ru.pocketbyte.kotlin.gradle.plugin.mpp_publish


fun String.upperFirstChar(): String {
    if (this[0].isLowerCase()) {
        return this[0].toUpperCase() + substring(1)
    }
    return this
}