# Kotlin Kydra Log
![Maven Central](https://img.shields.io/maven-central/v/ru.pocketbyte.kydra/kydra-log) [![License](https://img.shields.io/badge/License-Apache/2.0-blue.svg)](LICENSE) [![Claude Code Skill](https://img.shields.io/badge/Claude_Code-Skill-D97757)](https://github.com/PocketByte/kotlin-kydra-log/blob/master/.claude/skills/kydra-log/SKILL.md)

Kotlin Kydra Log - Kotlin Multiplatform Library that allows to write logs in common module.
The way logs are written is defined for each platform independently.

### Supported Targets

| Platform | Targets | Logger |
|----------|---------|--------|
| Android | `android` | LogCat |
| Android Native | `androidNativeArm32`, `androidNativeArm64`, `androidNativeX64`, `androidNativeX86` | LogCat |
| iOS | `iosArm64`, `iosX64`, `iosSimulatorArm64` | OSLog |
| macOS | `macosX64`, `macosArm64` | OSLog |
| watchOS | `watchosArm32`, `watchosArm64`, `watchosX64`, `watchosSimulatorArm64` | OSLog |
| tvOS | `tvosArm64`, `tvosX64`, `tvosSimulatorArm64` | OSLog |
| JavaScript | `js` (IR, browser + Node.js) | JS Console |
| Wasm | `wasmJs` (browser + Node.js), `wasmWasi` (Node.js) | `println` with timestamp |
| JVM | `jvm` | `println` with timestamp |
| Linux | `linuxX64`, `linuxArm64` | `println` with timestamp |
| Windows | `mingwX64` | `println` with timestamp |

### How to use

Add common library as dependency in common **`build.gradle`**:
```gradle
repositories {
    mavenCentral()
}
dependencies {
    ...
    implementation 'ru.pocketbyte.kydra:kydra-log:3.0.0'
}
```

Or in **`build.gradle.kts`** (Kotlin DSL):
```kotlin
repositories {
    mavenCentral()
}
dependencies {
    // other dependencies
    implementation("ru.pocketbyte.kydra:kydra-log:3.0.0")
}
```

Then you able to use KydraLog in common code:
```kotlin
KydraLog.info { "Info log message" }
KydraLog.debug { "Debug log message" }
KydraLog.warn { "Warning log message" }
KydraLog.error { "Error log message" }

// Optionally provide a tag as the first argument
KydraLog.info("MyTag") { "Info log message with tag" }
```

### Logging with format

If you want to log a formatted string, you should use Kotlin String Templates:
```kotlin
val count = 1
KydraLog.info { "Count is $count" }
```

### Logger initialization

Any logging via not initialized KydraLog will call initialisation with default Logger. But if you
want to initialize `KydraLog` with custom filtering you could use function 
`initDefault(level: LogLevel?, tags: Set<String?>?)`. For example, on Android platform can be used
different `LogLevel` filtering depending on build type:
```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            // No need to write debug logs in production build
            KydraLog.initDefault(LogLevel.INFO)
        }
    }
}
```
**IMPORTANT NOTE**: Initialization can be called only once. Re-initialization will throw
`IllegalStateException`. Any logging via not initialized KydraLog will call initialisation with
default Logger.

### Custom loggers

If you want to implement your own custom logger you should extend abstract class **`ru.pocketbyte.kydra.log.Logger`**:

```kotlin
class MyLogger: Logger() {

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        // Custom logging of message
        ...
    }
}
```
You can also use the abstract class **`ru.pocketbyte.kydra.log.AbsLogger`**.
This class splits log function into 2 functions with string and exception as a message parameter.

Then you should init KydraLog with your logger:

```kotlin
KydraLog.init(MyLogger())
```
To apply filter on your custom logger you could use operator `filtered`:
```kotlin
// Will log only error logs with tags "API_CORE" and "API_SOCKET"
KydraLog.init(MyLogger().filtered(LogLevel.ERROR, setOf("API_CORE", "API_SOCKET")))
```

To utilize several loggers at the same time you could use `LoggersSet`:
```kotlin
KydraLog.init(
    LoggersSet(
        AndroidLogger(),
        MyLogger().filtered(LogLevel.ERROR, setOf("API_CORE", "API_SOCKET"))
    ).filtered(if(BuildConfig.DEBUG) LogLevel.DEBUG  else LogLevel.INFO)
)
```
**Note:** In example above, second filter applies to whole set instead of applying to
`AndroidLogger` only.

### KydraLog class is not mandatory

You are not forced to use `KydraLog` object. It was designed to provide Plug & Play functionality.
If you wish, you can instantiate `Logger` as variable and use it for logging. Or you can use 
Dependency Injection to provide `Logger` instance. To get default console logger instance you could
use `DefaultLoggerFactory.create()`.

### Logger Toggle

To be able to switch on/off logger at any time you could use `LoggerToggle` wrapper.

```kotlin
val loggerToggle = LoggerToggle(myLogger)

loggerToggle.info { "This log will be shown." }
loggerToggle.enabled = false
loggerToggle.info { "This log will NOT be shown." }
```

### Logger Tag Transform

To override log tag or provide default tag you could use `withTag` or `withTagTransform` extensions.

```kotlin
// fooLogger uses "Foo" tag as default
val fooLogger = myLogger.withTag(default = "Foo")
fooLogger.info { "Hello Foo" }        // Log will be printed with tag "Foo"
fooLogger.info("Bar") { "Hello Foo" } // Log will be printed with tag "Bar"


// barLogger always uses "Bar" tag
val barLogger = myLogger.withTagTransform { "Bar" }
barLogger.info { "Hello Bar" }        // Log will be printed with tag "Bar"
barLogger.info("Foo") { "Hello Bar" } // Log will be printed with tag "Bar"
```

## License

```
Copyright © 2022 Denis Shurygin. All rights reserved.
Contacts: <mail@pocketbyte.ru>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
