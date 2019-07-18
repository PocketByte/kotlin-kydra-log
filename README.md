# Kotlin Hydra Log
Kotlin Hydra Log - Kotlin Multiplatform Library that allows to write logs in common module. The way how logs will written defines for each platform independently.

##### How to use:
Add common library as dependency in common **`build.gradle`**:
```gradle
repositories {
    maven { url "https://dl.bintray.com/pocketbyte/maven/" }
}
dependencies {
    ...
    implementation 'ru.pocketbyte.hydra:hydra-log:1.0.0'
}
```

Then you able to use HydraLog in common code:
```Kotlin
HydraLog.info("Info log message")
HydraLog.debug("Debug log message")
HydraLog.warn("Warning log message")
HydraLog.error("Error log message")
```
##### Logging with format:
If you would to log formatted string you should use kotlin String Templates:
```Kotlin
val count = 1
HydraLog.info("Count is $count")
```
To optimize logging with formatting you could use methods with function as parameter instead of string. It allow to skip filtered messages without calculating message string.
```Kotlin
HydraLog.info { "Count is $count" }
```
Here is the complex example that demonstrate logging of function.
```Kotlin
// Messages with level priority that lower than INFO will be filtered
HydraLog.initDefault(level= LogLevel.INFO)

var loggerMessage = "Hello"
HydraLog.error {
    loggerMessage = "Hello from Error"
    "Error log printed"
}
HydraLog.debug {
    loggerMessage = "Hello from Debug"
    "Debug log printed"
}
HydraLog.info { "Logger say: $loggerMessage" }
```
In the logs will be printed following list of messages:
```
Error log printed
Logger say: Hello from Error
```
##### Logger initialization:
Any logging via not initialized HydraLog will call initialisation with default Logger. But if you want to initialize `HydraLog` with custom filtering you could use function `initDefault(level: LogLevel?, tags: Set<String?>?)`.
For example, on Android platform can be used different `LogLevel` filtering depending on build type:
```Kotlin
calss MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            // No need to write debug logs in production build
            HydraLog.initDefault(LogLevel.INFO)
        }
    }
}
```
**IMPORTANT NOTE**: Initialization can be called only once. Re-initialization will throw `IllegalStateException`. Any logging via not initialized HydraLog will call initialisation with default Logger.

##### Custom loggers:
If you want to implement your own custom logger you should implement interface **`ru.pocketbyte.hydra.log.Logger`**:

```Kotlin
class MyLogger: Logger {

    override fun log(level: LogLevel, tag: String?, message: String) {
        // Custom logging of message
        ...
    }

    fun log(level: LogLevel, tag: String?, exception: Throwable) {
        // Custom logging of exception
        ...
    }

    fun log(level: LogLevel, tag: String?, function: () -> String) {
        // Custom logging of function

        // For example
        log(level, tag, function())
    }
}
```

Then you should init HydraLog with your logger:

```Kotlin
HydraLog.init(MyLogger())
```
To apply filter on your custom logger you could use operator `filtered`:
```Kotlin
// Will loged only error logs with tags "API_CORE" and "API_SOCKET"
HydraLog.init(MyLogger().filtered(LogLevel.ERROR, setOf("API_CORE", "API_SOCKET")))
```

To utilize several loggers at the same time could be used `LoggersSet`:
```Kotlin
HydraLog.init(
    LoggersSet(
        AndroidLogger(),
        MyLogger().filtered(LogLevel.ERROR, setOf("API_CORE", "API_SOCKET"))
    ).filtered(if(BuildConfig.DEBUG) LogLevel.DEBUG  else LogLevel.INFO)
)
```
**Note:** In example above, second filter applies to whole set instead of applying to `AndroidLogger` only. It helps to optimize logging with functions that was described in section "Logging with format", because `LoggersSet` calculate function and pass it's result into method `log(level, tag, message)` of wrapped loggers.

##### HydraLog is not mandatory:
You are not forced to use `HydraLog` object. It was designed to provide Plug and Play functionality. If you wish, you can instantiate `Logger` as variable and use it for logging. Or you can use Dependency Injection to provide `Logger` instance. To make sure that `HydraLog` is not used in your application you can init `HydraLog` with `ThrowExceptionLogger`:
```Kotlin
HydraLog.init(ThrowExceptionLogger("HydraLog object is forbidden. Please use DI to get Logger instance."))
```
It will let to know for other developers that should be used another way to receive `Logger` instance.

## License

```
Copyright © 2017 Denis Shurygin. All rights reserved.
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


