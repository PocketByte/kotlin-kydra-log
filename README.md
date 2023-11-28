# Kotlin Kydra Log
![Maven Central](https://img.shields.io/maven-central/v/ru.pocketbyte.kydra/kydra-log) [![License](https://img.shields.io/badge/License-Apache/2.0-blue.svg)](LICENSE)

Kotlin Kydra Log - Kotlin Multiplatform Library that allows to write logs in common module.
The way how logs will written defines for each platform independently.

Fully supported platforms:
- Android and AndroidNative(Arm32, Arm64, X64, X86) using LogCat;
- JavaScript using JS Console;
- Apple based: iOS, MacOS, Watch and TV using OSLog.

Also, not well by using `println` with timestamp:
- Java;
- Linux based: X64, Arm64;
- Windows X64;

### How to use:
Add common library as dependency in common **`build.gradle`**:
```gradle
repositories {
    mavenCentral()
}
dependencies {
    ...
    implementation 'ru.pocketbyte.kydra:kydra-log:2.0.2'
}
```

Then you able to use KydraLog in common code:
```Kotlin
KydraLog.info("Info log message")
KydraLog.debug("Debug log message")
KydraLog.warn("Warning log message")
KydraLog.error("Error log message")
```
### Logging with format:
If you would to log formatted string you should use kotlin String Templates:
```Kotlin
val count = 1
KydraLog.info("Count is $count")
```
To optimize logging with formatting you could use methods with function as parameter instead of
string. It allow to skip filtered messages without calculating message string.
```Kotlin
KydraLog.info { "Count is $count" }
```
Here is the complex example that demonstrate logging of function.
```Kotlin
// Messages with level priority that lower than INFO will be filtered
KydraLog.initDefault(level= LogLevel.INFO)

var loggerMessage = "Hello"
KydraLog.error {
    loggerMessage = "Hello from Error"
    "Error log printed"
}
KydraLog.debug {
    loggerMessage = "Hello from Debug"
    "Debug log printed"
}
KydraLog.info { "Logger say: $loggerMessage" }
```
In the logs will be printed following list of messages:
```
Error log printed
Logger say: Hello from Error
```
### Logger initialization:
Any logging via not initialized KydraLog will call initialisation with default Logger. But if you
want to initialize `KydraLog` with custom filtering you could use function 
`initDefault(level: LogLevel?, tags: Set<String?>?)`. For example, on Android platform can be used
different `LogLevel` filtering depending on build type:
```Kotlin
calss MyApplication : Application() {
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

### Custom loggers:
If you want to implement your own custom logger you should extend abstract class **`ru.pocketbyte.kydra.log.Logger`**:

```Kotlin
class MyLogger: Logger() {

    override fun doLog(level: LogLevel, tag: String?, message: Any) {
        // Custom logging of message
        ...
    }
}
```
Also you can use abstract class **`ru.pocketbyte.kydra.log.AbsLogger`**.
This class splits log function into 2 functions with string and exception as a message parameter.

Then you should init KydraLog with your logger:

```Kotlin
KydraLog.init(MyLogger())
```
To apply filter on your custom logger you could use operator `filtered`:
```Kotlin
// Will loged only error logs with tags "API_CORE" and "API_SOCKET"
KydraLog.init(MyLogger().filtered(LogLevel.ERROR, setOf("API_CORE", "API_SOCKET")))
```

To utilize several loggers at the same time could be used `LoggersSet`:
```Kotlin
KydraLog.init(
    LoggersSet(
        AndroidLogger(),
        MyLogger().filtered(LogLevel.ERROR, setOf("API_CORE", "API_SOCKET"))
    ).filtered(if(BuildConfig.DEBUG) LogLevel.DEBUG  else LogLevel.INFO)
)
```
**Note:** In example above, second filter applies to whole set instead of applying to
`AndroidLogger` only.

### KydraLog class is not mandatory:
You are not forced to use `KydraLog` object. It was designed to provide Plug and Play functionality.
If you wish, you can instantiate `Logger` as variable and use it for logging. Or you can use 
Dependency Injection to provide `Logger` instance. To get default console logger instance you could
use `DefaultLoggerFactory.build()`.

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
