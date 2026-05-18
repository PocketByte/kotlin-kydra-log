# Migration Guide — 3.0.0

Version 3.0.0 reorganises the library's package structure, removes long-deprecated classes, and
escalates several previously `WARNING`-level deprecations to `ERROR` level — these will become
compile errors if still in use.

## New package layout

| Package | Contents |
|---|---|
| `ru.pocketbyte.kydra.log` | Core API: `Logger`, `LogLevel`, `AbsLogger`, `ThrowableWithMessage`, `LoggerStub`, `ThrowExceptionLogger`, extension functions |
| `ru.pocketbyte.kydra.log.wrapper` | Decorator infrastructure: `AbsLoggerWrapper`, `FilteredLoggerWrapper`, `LoggerTagTransform`, `LoggerToggle`, `InitializableLogger` and their builder/init extensions |
| `ru.pocketbyte.kydra.log.collection` | Multi-logger containers: `LoggersSet`, `ExtendableLoggersSet` and the `addDefault` extension |
| `ru.pocketbyte.kydra.log.print` | Unchanged: `Printer`, `LogMessageFormatter`, `SimplePrintLogger`, formatters |

---

## Changed imports

### `ru.pocketbyte.kydra.log.wrapper`

| Class / function | Old import | New import |
|---|---|---|
| `AbsLoggerWrapper` | `ru.pocketbyte.kydra.log.AbsLoggerWrapper` | `ru.pocketbyte.kydra.log.wrapper.AbsLoggerWrapper` |
| `FilteredLoggerWrapper` | `ru.pocketbyte.kydra.log.FilteredLoggerWrapper` | `ru.pocketbyte.kydra.log.wrapper.FilteredLoggerWrapper` |
| `filtered()` | `ru.pocketbyte.kydra.log.filtered` | `ru.pocketbyte.kydra.log.wrapper.filtered` |
| `LoggerTagTransform` | `ru.pocketbyte.kydra.log.LoggerTagTransform` | `ru.pocketbyte.kydra.log.wrapper.LoggerTagTransform` |
| `withTag()` | `ru.pocketbyte.kydra.log.withTag` | `ru.pocketbyte.kydra.log.wrapper.withTag` |
| `withTagTransform()` | `ru.pocketbyte.kydra.log.withTagTransform` | `ru.pocketbyte.kydra.log.wrapper.withTagTransform` |
| `LoggerToggle` | `ru.pocketbyte.kydra.log.LoggerToggle` | `ru.pocketbyte.kydra.log.wrapper.LoggerToggle` |
| `InitializableLogger` | `ru.pocketbyte.kydra.log.InitializableLogger` | `ru.pocketbyte.kydra.log.wrapper.InitializableLogger` |
| `initDefault()` | `ru.pocketbyte.kydra.log.initDefault` | `ru.pocketbyte.kydra.log.wrapper.initDefault` |
| `initOrIgnore()` | `ru.pocketbyte.kydra.log.initOrIgnore` | `ru.pocketbyte.kydra.log.wrapper.initOrIgnore` |

### `ru.pocketbyte.kydra.log.collection`

| Class / function | Old import | New import |
|---|---|---|
| `LoggersSet` | `ru.pocketbyte.kydra.log.LoggersSet` | `ru.pocketbyte.kydra.log.collection.LoggersSet` |
| `ExtendableLoggersSet` | `ru.pocketbyte.kydra.log.ExtendableLoggersSet` | `ru.pocketbyte.kydra.log.collection.ExtendableLoggersSet` |
| `addDefault()` | `ru.pocketbyte.kydra.log.addDefault` | `ru.pocketbyte.kydra.log.collection.addDefault` |

### Unchanged

The following remain in `ru.pocketbyte.kydra.log` and require no import changes:

`Logger`, `LogLevel`, `AbsLogger`, `ThrowableWithMessage`, `LoggerStub`, `ThrowExceptionLogger`,
`KydraLog` (plug-and-play module), all `Logger` extension functions (`info`, `debug`, `warning`, `error`).

---

## Removed API

### `AbsPrintLogger` — removed

`AbsPrintLogger` has been removed. It was deprecated since an earlier release with a `WARNING` level.

**Replacement:** use `SimplePrintLogger` (`ru.pocketbyte.kydra.log.print`) with one of the
ready-made `Printer` and `LogMessageFormatter` implementations that the library already provides
for each platform.

```kotlin
// Before (2.x)
class MyLogger : AbsPrintLogger() {
    override fun printLog(message: String) { /* write somewhere */ }
    override fun stackTrace(e: Throwable) = e.stackTraceToString()
    override fun qualifiedName(e: Throwable) = e::class.qualifiedName ?: "Unknown"
}

// After (3.0) — stdout, JVM / Android
val myLogger = SimplePrintLogger(
    printer = SimplePrinter(),               // println()-based, available on all platforms
    logMessageFormatter = JvmLogMessageFormatter()  // timestamp + throwable formatting for JVM
)

// After (3.0) — rotating file output, JVM only
val myLogger = SimplePrintLogger(
    printer = JvmFilePrinter(
        maxSizeBytes = 1_000_000L,
        maxFolderSizeBytes = 5_000_000L,
        logDirectory = Path("logs")
    ),
    logMessageFormatter = JvmLogMessageFormatter()
)
```

Available `LogMessageFormatter` implementations per platform:

| Platform | Class |
|---|---|
| JVM / Android | `JvmLogMessageFormatter` (`ru.pocketbyte.kydra.log.print`) |
| Native | `NativeLogMessageFormatter` (`ru.pocketbyte.kydra.log.print`) |
| Wasm | `WasmLogMessageFormatter` (`ru.pocketbyte.kydra.log.print`) |

Available `Printer` implementations:

| Scope | Class |
|---|---|
| All platforms | `SimplePrinter` — writes via `println()` |
| JVM only | `JvmFilePrinter` — writes to rotating log files |

If none of the built-in formatters fit, implement `LogMessageFormatter` directly or extend
`AbsLogMessageFormatter` to keep the standard level/tag/throwable structure and only override
`getTimeStamp()` and `appendThrowable()`.

### `Logger.log(level, tag, message, omitFilter)` — removed

Internal API that was never intended for public use.

```kotlin
// Before (2.x) — should not have been called directly
logger.log(level, tag, message, omitFilter = true)

// After (3.0)
logger.log(level, tag) { message }
```

### `DefaultLoggerFactory.build(level, tags)` — removed

```kotlin
// Before (2.x)
val logger = DefaultLoggerFactory.build(LogLevel.INFO, setOf("MyTag"))

// After (3.0)
val logger = DefaultLoggerFactory.create().filtered(LogLevel.INFO, setOf("MyTag"))
```

### `NSLogger` — removed (Apple targets only)

`NSLogger` has been removed. It was deprecated at `ERROR` level and is superseded by `AppleLogger`,
which uses the modern `os_log` API instead of `NSLog`.

```kotlin
// Before (2.x)
val logger = NSLogger()

// After (3.0)
val logger = AppleLogger()
```

---

## Escalated deprecations (now `ERROR` level)

These APIs still exist but now cause compile errors. They will be fully removed in a future release.

`Logger` member:

| Now `ERROR` | Replacement |
|---|---|
| `log(level, tag, message: Any)` | `log(level, tag) { message }` |

`Logger` extension functions:

| Now `ERROR` | Replacement |
|---|---|
| `log(level, message)` | `log(level) { message }` |
| `info(tag, message)` | `info(tag) { message }` |
| `info(message)` | `info { message }` |
| `debug(tag, message)` | `debug(tag) { message }` |
| `debug(message)` | `debug { message }` |
| `warn(tag, message)` | `warn(tag) { message }` |
| `warn(message)` | `warn { message }` |
| `error(tag, message)` | `error(tag) { message }` |
| `error(message)` | `error { message }` |

Other:

| Now `ERROR` | Replacement |
|---|---|
| `DefaultLoggerFactory.build()` | `DefaultLoggerFactory.create()` |
| `withTag(defaultTag: String)` | `withTag(default = defaultTag)` |

---

## Typical migration steps

1. **Build once** — the compiler reports every broken import and every `ERROR`-level deprecation.

2. **Fix moved imports** — bulk-replace the old package prefix for each group:

   Wrapper types (`AbsLoggerWrapper`, `FilteredLoggerWrapper`, `LoggerTagTransform`, `LoggerToggle`,
   `InitializableLogger`, and extension functions `filtered`, `withTag`, `withTagTransform`,
   `initDefault`, `initOrIgnore`):
   ```text
   ru.pocketbyte.kydra.log.  →  ru.pocketbyte.kydra.log.wrapper.
   ```
   Collection types (`LoggersSet`, `ExtendableLoggersSet`, `addDefault`):
   ```text
   ru.pocketbyte.kydra.log.  →  ru.pocketbyte.kydra.log.collection.
   ```
   For files that use many of these types, wildcard imports keep things concise:
   ```kotlin
   import ru.pocketbyte.kydra.log.wrapper.*
   import ru.pocketbyte.kydra.log.collection.*
   ```

3. **Replace removed APIs:**
   - `AbsPrintLogger` subclasses → `SimplePrintLogger` (see [AbsPrintLogger — removed](#absprintlogger--removed) above)
   - `logger.log(level, tag, message, omitFilter = true)` → `logger.log(level, tag) { message }`
   - `DefaultLoggerFactory.build(level, tags)` → `DefaultLoggerFactory.create().filtered(level, tags)`
   - `NSLogger()` → `AppleLogger()` *(Apple targets only)*

4. **Fix `ERROR`-level deprecations** (compile errors since 3.0):
   - `log(level, tag, message: Any)` → `log(level, tag) { message }`
   - `info/debug/warn/error(tag, message)` → `info/debug/warn/error(tag) { message }`
   - `info/debug/warn/error(message)` → `info/debug/warn/error { message }`
   - `DefaultLoggerFactory.build()` → `DefaultLoggerFactory.create()`
   - `withTag(defaultTag)` → `withTag(default = defaultTag)`

5. **Build again** — no further source changes are required.
