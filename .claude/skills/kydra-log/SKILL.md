---
name: kydra-log
description: Use this skill whenever the user works with the Kotlin Kydra Log library (kydra-log), including adding logging to Kotlin Multiplatform (KMP) or Android projects, initializing KydraLog, creating custom loggers, using log levels, filtering logs by level or tag, combining multiple loggers, using LoggerToggle, tag transforms, or any task that involves `ru.pocketbyte.kydra` imports or `KydraLog` usage.
version: 1.3.0
---

# Kotlin Kydra Log

Kotlin Multiplatform logging library. Supports Android, iOS/macOS/tvOS/watchOS, JS, JVM, Linux, Windows.

---

## Quick Start

```kotlin
// build.gradle.kts (commonMain)
implementation("ru.pocketbyte.kydra:kydra-log:3.0.0")
```

```kotlin
import ru.pocketbyte.kydra.log.KydraLog
import ru.pocketbyte.kydra.log.withMessage
import ru.pocketbyte.kydra.log.debug
import ru.pocketbyte.kydra.log.info
import ru.pocketbyte.kydra.log.warn
import ru.pocketbyte.kydra.log.error

KydraLog.debug { "message" }
KydraLog.info("TAG") { "tagged message" }
try {
    riskyOperation()
} catch (e: Exception) {
    KydraLog.warn { e withMessage "Something went wrong" }
}
KydraLog.error { "fatal: $details" }
```

Works without any initialization — auto-uses the platform default logger.

---

## Usage Modes

Choose the mode before adding the dependency — it determines which artifact to use.

### Mode 1: KydraLog singleton (`kydra-log`) — default

Use this mode unless the user explicitly mentions DI, a custom singleton, SDK/library development, or `kydra-log-manual`.

Global `KydraLog` object, ready to use out of the box. Best for applications.

```kotlin
// build.gradle.kts (commonMain)
implementation("ru.pocketbyte.kydra:kydra-log:3.0.0")
```

```kotlin
import ru.pocketbyte.kydra.log.KydraLog
import ru.pocketbyte.kydra.log.info

KydraLog.init(MyLogger())   // once, at app startup
KydraLog.info { "hello" }
```

### Mode 2: Custom singleton via `InitializableLogger` (`kydra-log-manual`)

Own singleton object with the same lifecycle control as `KydraLog`. Best for SDKs and modules that want their own isolated logger.

```kotlin
// build.gradle.kts (commonMain)
implementation("ru.pocketbyte.kydra:kydra-log-manual:3.0.0")
```

```kotlin
import ru.pocketbyte.kydra.log.Logger
import ru.pocketbyte.kydra.log.DefaultLoggerFactory
import ru.pocketbyte.kydra.log.info
import ru.pocketbyte.kydra.log.wrapper.InitializableLogger

object MyLog : InitializableLogger<Logger>() {
    override val defaultLogger: Logger by lazy { DefaultLoggerFactory.create() }
}

// usage — identical to KydraLog
MyLog.init(MyLogger())
MyLog.info { "hello" }
```

### Mode 3: DI — no global object (`kydra-log-manual`)

`Logger` instances are created explicitly and injected. Best for testable, modular code with no global state.

```kotlin
// build.gradle.kts (commonMain)
implementation("ru.pocketbyte.kydra:kydra-log-manual:3.0.0")
```

```kotlin
import ru.pocketbyte.kydra.log.Logger
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.DefaultLoggerFactory
import ru.pocketbyte.kydra.log.debug
import ru.pocketbyte.kydra.log.wrapper.filtered
import ru.pocketbyte.kydra.log.wrapper.withTag

// Create a configured logger instance and inject it into classes
val logger: Logger = DefaultLoggerFactory.create()
    .filtered(LogLevel.DEBUG)

class UserRepository(logger: Logger) {
    private val logger = logger.withTag(default = "UserRepo")

    fun fetchUser(id: String) {
        logger.debug { "Fetching user $id" }
    }
}
```

In tests, inject `LoggerStub` to silence logs or `ThrowExceptionLogger` to fail on unexpected log calls.

---

## Decision Guide

### Which initialization to use?

| Scenario | Method |
|----------|--------|
| Basic use, no customization needed | nothing — auto-init on first log |
| Need level filtering only (e.g. no DEBUG in release) | `KydraLog.initDefault(level = LogLevel.INFO)` |
| Need level + tag filtering | `KydraLog.initDefault(level = ..., tags = setOf(...))` |
| Custom logger implementation | `KydraLog.init(MyLogger())` |
| May be called multiple times (library code) | `KydraLog.initOrIgnore(MyLogger())` |

### Which base class for custom logger?

| Need | Class |
|------|-------|
| Single `doLog` for all message types | `Logger` |
| Separate handlers for String / Throwable / both | `AbsLogger` |
| No-op / silent logger | `LoggerStub` (built-in) |
| Throw instead of log (testing) | `ThrowExceptionLogger` (built-in) |

---

## Logging API

All methods are extension functions on `Logger` — works the same for `KydraLog`, a custom singleton, or an injected `logger` instance. All take a lazy lambda — evaluated only if the message will be logged.

```kotlin
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.withMessage
import ru.pocketbyte.kydra.log.debug
import ru.pocketbyte.kydra.log.info
import ru.pocketbyte.kydra.log.warn
import ru.pocketbyte.kydra.log.error
import ru.pocketbyte.kydra.log.log

// Without tag
logger.debug { "msg" }
logger.info  { "msg" }
logger.warn  { "msg" }
logger.error { "msg" }

// With tag
logger.debug("TAG") { "msg" }
logger.info("HTTP") { "response: $code" }

// With exception + message (infix)
logger.error { e withMessage "Failed to load user" }

// Explicit level
logger.log(LogLevel.INFO) { "msg" }
logger.log(LogLevel.INFO, "TAG") { "msg" }
```

`logger` here is any `Logger` instance: `KydraLog`, your own singleton, or an injected value.

| `LogLevel` | Priority | Extension function | `log(level)` equivalent |
|------------|----------|--------------------|-------------------------|
| `DEBUG`    | 1        | `.debug { }` / `.debug("tag") { }` | `log(LogLevel.DEBUG) { }` |
| `INFO`     | 2        | `.info { }` / `.info("tag") { }`   | `log(LogLevel.INFO) { }` |
| `WARNING`  | 3        | `.warn { }` / `.warn("tag") { }`   | `log(LogLevel.WARNING) { }` |
| `ERROR`    | 4        | `.error { }` / `.error("tag") { }` | `log(LogLevel.ERROR) { }` |

Note: the extension function is `warn {}` but the enum value is `LogLevel.WARNING`.

---

## ThrowableWithMessage

`ThrowableWithMessage` pairs an exception with a human-readable context message. Use it whenever catching an exception to preserve both the message and the full stack trace in the log output.

```kotlin
import ru.pocketbyte.kydra.log.withMessage
import ru.pocketbyte.kydra.log.error

try {
    userRepository.save(user)
} catch (e: Exception) {
    logger.error { e withMessage "Failed to save user ${user.id}" }
}
```

`e withMessage "msg"` returns `ThrowableWithMessage(message = "msg", throwable = e)`. Its `toString()` outputs:
```
Failed to save user 42
java.io.IOException: Connection reset
    at ...
```

So the log always contains both the context and the full stack trace.

**Receiver order**: the `Throwable` is on the left, the message string is on the right:
```kotlin
e withMessage "context"   // correct
"context" withMessage e   // compile error
```

Always create `ThrowableWithMessage` **inside** the lambda — constructing it outside defeats the lazy evaluation and allocates the object even when the log is filtered:
```kotlin
// correct — created only if the log passes through
logger.error { e withMessage "Failed to save user ${user.id}" }

// wrong — e withMessage "..." is evaluated eagerly, before the filter check
val entry = e withMessage "Failed to save user ${user.id}"
logger.error { entry }
```

`withMessage` is a top-level infix extension on `Throwable` — importing the class `ThrowableWithMessage` alone does **not** bring `withMessage` into scope. Always import them separately when needed:
```kotlin
import ru.pocketbyte.kydra.log.ThrowableWithMessage  // the class
import ru.pocketbyte.kydra.log.withMessage            // the infix function
```

---

## Initialization (Mode 1 / Mode 2 only)

> Mode 3 (DI) skips this section — logger instances are created and composed directly, no `init()` needed.

`init()` must be called before first log. Any log call before init triggers auto-init with the default platform logger.

```kotlin
import ru.pocketbyte.kydra.log.KydraLog
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.wrapper.initDefault
import ru.pocketbyte.kydra.log.wrapper.initOrIgnore

// Level filter only — suppress DEBUG in production
KydraLog.initDefault(level = LogLevel.INFO)

// Level + tag filter (null in set = allow untagged logs)
KydraLog.initDefault(level = LogLevel.DEBUG, tags = setOf("API", "DB", null))

// Custom logger
KydraLog.init(MyLogger())

// Safe for library code — silently skips if already initialized
KydraLog.initOrIgnore(MyLogger())
```

Replace `KydraLog` with your own singleton object for Mode 2.

`init()` throws `IllegalStateException` on re-call. Check `KydraLog.isInitialized` if needed.

**Android pattern** — call in `Application.onCreate()` before any background work starts:
```kotlin
import ru.pocketbyte.kydra.log.KydraLog
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.wrapper.initDefault

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KydraLog.initDefault(
            level = if (BuildConfig.DEBUG) LogLevel.DEBUG else LogLevel.INFO
        )
    }
}
```

---

## Custom Logger

### `Logger` — single entry point for all message types

```kotlin
import ru.pocketbyte.kydra.log.Logger
import ru.pocketbyte.kydra.log.LogLevel

class MyLogger : Logger() {
    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        // message is String, Throwable, or ThrowableWithMessage
        println("[$level]${tag?.let { " $it" } ?: ""}: $message")
    }
}
```

### `AbsLogger` — separate handlers per message type

Prefer this when the output system distinguishes strings from exceptions (e.g. crash reporters):

```kotlin
import ru.pocketbyte.kydra.log.AbsLogger
import ru.pocketbyte.kydra.log.LogLevel

class MyLogger : AbsLogger() {
    override fun doLog(level: LogLevel, tag: String?, message: String) { }
    override fun doLog(level: LogLevel, tag: String?, exception: Throwable) { }
    override fun doLog(level: LogLevel, tag: String?, message: String, exception: Throwable) { }
}
```

---

## Composition

All wrappers follow the same decorator pattern: `logger.wrapper(...)` returns a new `Logger`. They can be chained.

### Filter

```kotlin
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.wrapper.filtered

// Minimum level
MyLogger().filtered(LogLevel.WARNING)

// Level + allowed tags (null = untagged logs pass through)
MyLogger().filtered(LogLevel.ERROR, setOf("API_CORE", "API_SOCKET", null))

// Custom predicate
MyLogger().filtered { level, tag -> level >= LogLevel.INFO && tag != "VERBOSE" }
```

### Multiple loggers — `LoggersSet`

```kotlin
import ru.pocketbyte.kydra.log.KydraLog
import ru.pocketbyte.kydra.log.LogLevel
import ru.pocketbyte.kydra.log.AndroidLogger
import ru.pocketbyte.kydra.log.collection.LoggersSet
import ru.pocketbyte.kydra.log.collection.ExtendableLoggersSet
import ru.pocketbyte.kydra.log.collection.addDefault
import ru.pocketbyte.kydra.log.wrapper.filtered

// Immutable set
KydraLog.init(
    LoggersSet(
        AndroidLogger(),
        MyLogger().filtered(LogLevel.ERROR, setOf("API_CORE"))
    ).filtered(if (BuildConfig.DEBUG) LogLevel.DEBUG else LogLevel.INFO)
    // outer .filtered() applies to the whole set
)

// Mutable set — add loggers at runtime
val loggers = ExtendableLoggersSet()
loggers.addLogger(AndroidLogger())
loggers.addDefault(LogLevel.DEBUG) // adds filtered default platform logger
KydraLog.init(loggers)
```

### Toggle

Switch logging on/off at runtime without re-initialization:

```kotlin
import ru.pocketbyte.kydra.log.KydraLog
import ru.pocketbyte.kydra.log.wrapper.LoggerToggle

val toggle = LoggerToggle(MyLogger())
KydraLog.init(toggle)

toggle.enabled = false  // suppress all logs
toggle.enabled = true   // resume
```

### Tag transform

```kotlin
import ru.pocketbyte.kydra.log.info
import ru.pocketbyte.kydra.log.wrapper.withTag
import ru.pocketbyte.kydra.log.wrapper.withTagTransform

// Default tag when none provided; explicit tag still overrides it
val logger = myLogger.withTag(default = "MyModule")
logger.info { "tagged 'MyModule'" }
logger.info("Other") { "tagged 'Other'" }

// Prefix + default + postfix
val logger = myLogger.withTag(prefix = "[", default = "App", postfix = "]")
// all logs tagged "[App]"

// Fully custom transform — forces all logs to use the result, ignoring caller's tag
val logger = myLogger.withTagTransform { _ -> "ForcedTag" }
```

---

## Pitfalls

- **`init()` throws on re-call.** Use `initOrIgnore()` in library code or modules that don't own the logger lifecycle.
- **`warn {}` ≠ `LogLevel.WARN`.** The method is `warn`, the enum value is `LogLevel.WARNING`.
- **`null` tag is valid.** In filter sets, `null` means "allow logs without a tag". Omitting it silently drops untagged logs.
- **Lambdas are lazy.** String interpolation inside `{ }` is not evaluated if the log is filtered out — don't put side effects there.
- **Create message objects inside the lambda.** Any object that exists only for the log message (e.g. `e withMessage "msg"`, string builders, data snapshots) must be constructed inside `{ }`, not before the call — otherwise it's allocated even when the log is filtered out.
- **`withTagTransform` overrides the caller's tag.** Use `withTag(default = ...)` if you only want a fallback.

---

## Imports Reference

The library uses three packages. Always include the right one — missing `.wrapper` causes "unresolved reference" on extension functions.

**`ru.pocketbyte.kydra.log`**

| Class / function | Import |
|-----------------|--------|
| `KydraLog` | `ru.pocketbyte.kydra.log.KydraLog` |
| `LogLevel` | `ru.pocketbyte.kydra.log.LogLevel` |
| `Logger` | `ru.pocketbyte.kydra.log.Logger` |
| `AbsLogger` | `ru.pocketbyte.kydra.log.AbsLogger` |
| `LoggerStub` | `ru.pocketbyte.kydra.log.LoggerStub` |
| `ThrowExceptionLogger` | `ru.pocketbyte.kydra.log.ThrowExceptionLogger` |
| `ThrowableWithMessage` | `ru.pocketbyte.kydra.log.ThrowableWithMessage` |
| `withMessage` infix | `ru.pocketbyte.kydra.log.withMessage` |
| `DefaultLoggerFactory` | `ru.pocketbyte.kydra.log.DefaultLoggerFactory` |
| `AndroidLogger` | `ru.pocketbyte.kydra.log.AndroidLogger` |
| `AndroidNativeLogger` | `ru.pocketbyte.kydra.log.AndroidNativeLogger` |
| `AppleLogger` | `ru.pocketbyte.kydra.log.AppleLogger` |
| `JsLogger` | `ru.pocketbyte.kydra.log.JsLogger` |
| `PrintLogger` | `ru.pocketbyte.kydra.log.PrintLogger` |
| `.debug()` extension | `ru.pocketbyte.kydra.log.debug` |
| `.info()` extension | `ru.pocketbyte.kydra.log.info` |
| `.warn()` extension | `ru.pocketbyte.kydra.log.warn` |
| `.error()` extension | `ru.pocketbyte.kydra.log.error` |
| `.log()` extension | `ru.pocketbyte.kydra.log.log` |

**`ru.pocketbyte.kydra.log.wrapper`**

| Class / function | Import |
|-----------------|--------|
| `InitializableLogger` | `ru.pocketbyte.kydra.log.wrapper.InitializableLogger` |
| `LoggerToggle` | `ru.pocketbyte.kydra.log.wrapper.LoggerToggle` |
| `.filtered()` extension | `ru.pocketbyte.kydra.log.wrapper.filtered` |
| `.withTag()` extension | `ru.pocketbyte.kydra.log.wrapper.withTag` |
| `.withTagTransform()` extension | `ru.pocketbyte.kydra.log.wrapper.withTagTransform` |
| `.initDefault()` extension | `ru.pocketbyte.kydra.log.wrapper.initDefault` |
| `.initOrIgnore()` extension | `ru.pocketbyte.kydra.log.wrapper.initOrIgnore` |

**`ru.pocketbyte.kydra.log.collection`**

| Class / function | Import |
|-----------------|--------|
| `LoggersSet` | `ru.pocketbyte.kydra.log.collection.LoggersSet` |
| `ExtendableLoggersSet` | `ru.pocketbyte.kydra.log.collection.ExtendableLoggersSet` |
| `.addDefault()` extension | `ru.pocketbyte.kydra.log.collection.addDefault` |

**Wildcard shorthand** (covers all wrappers and extensions):
```kotlin
import ru.pocketbyte.kydra.log.*
import ru.pocketbyte.kydra.log.wrapper.*
import ru.pocketbyte.kydra.log.collection.*
```

---

## Platform Reference

| Platform | Logger class | Output |
|----------|-------------|--------|
| Android | `AndroidLogger` | LogCat |
| AndroidNative | `AndroidNativeLogger` | NDK log |
| iOS / macOS / watchOS / tvOS | `AppleLogger` | OSLog |
| JavaScript | `JsLogger` | console |
| JVM / Linux / Windows | `PrintLogger` | println |

`DefaultLoggerFactory.create()` returns the correct implementation automatically.