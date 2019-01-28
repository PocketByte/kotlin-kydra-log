# Kotlin Hydra Log
Kotlin Hydra Log - Kotlin Multiplatform Library that allows to write logs in common module. The way how logs will written defines for each platform independently.

##### How to use in Common module:
First add common library as dependency in common **`build.gradle`**:
```gradle
dependencies {
    ...
    implementation 'ru.pocketbyte.hydra.log:hydra-log-common:0.1.1'
}
```
Then you able to use HydraLog in common code:
```Kotlin
HydraLog.debug("TEST_TAG", "Debug log message")
HydraLog.info("TEST_TAG", "Info log message")
```

##### How to use in Android module:
To make it work in Android add following dependency into Android **`build.gradle`**:
```gradle
dependencies {
    ...
    implementation 'ru.pocketbyte.hydra.log:hydra-log-android:0.1.1'
}
```
Then init HydraLog in Application class:
```Kotlin
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        HydraLog.initDefault()
    }
}
```

##### How to use in iOS module:
To make it work in iOS add following dependency into iOS **`build.gradle`**:
```gradle
dependencies {
    ...
    implementation 'ru.pocketbyte.hydra.log:hydra-log-ios:0.1.1'
}
```
No need to init HydraLog as it was done for Android, iOS library will do it automatically.

##### Custom loggers:
If you want to implement your own custom logger you should implement interface **`ru.pocketbyte.hydra.log.Logger`**:
```Kotlin
class MyLogger: Logger {

    override fun log(level: LogLevel, tag: String?, message: String, vararg arguments: Any) {
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


