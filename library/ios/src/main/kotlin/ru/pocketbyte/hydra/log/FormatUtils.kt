package ru.pocketbyte.hydra.log

import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.darwin.*
import platform.Foundation.*

// Taken from
// https://github.com/JetBrains/kotlin-native/issues/1834#issuecomment-409837732
internal fun format(format: String, vararg args: Any?): String {
    val cfString: CFStringRef = CFBridgingRetain(format)!!.reinterpret()
    try {
        val encodedArgs = args.map {
            if (it is String)
            // Prevent from being converted to C string when passing as C variadic argument:
                object : NSObject() {
                    override fun description(): String? = it
                }
            else it
        }

        val cfFormattedString = CFStringCreateWithFormat(null, null, cfString, *encodedArgs.toTypedArray())
        return CFBridgingRelease(cfFormattedString) as String
    } finally {
        CFRelease(cfString)
    }
}