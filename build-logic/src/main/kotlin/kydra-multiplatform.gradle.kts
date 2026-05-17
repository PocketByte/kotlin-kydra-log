@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinWasmTargetDsl

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

android {
    compileSdk = properties["ANDROID_SDK_COMPILE"].toString().toInt()
    namespace = "ru.pocketbyte.kydra"

    defaultConfig {
        minSdk = properties["ANDROID_SDK_MIN"].toString().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile(
                "${project.projectDir.absoluteFile}/src/androidMain/AndroidManifest.xml"
            )
        }
    }

    buildTypes {
        getByName("release") { }
        getByName("debug") { }
    }
}

kotlin {
    // =================================
    // Common Source Sets

    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("stdlib"))
            }
        }

        nativeMain {
            dependsOn(commonMain.get())
        }

        commonTest {
            dependencies {
                api(kotlin("test"))
            }
        }

        nativeTest {
            dependsOn(commonTest.get())
        }
    }

    // =================================
    // JVM based targets

    jvm()
    androidTarget {
        publishLibraryVariants("release", "debug")
    }

    jvmToolchain(11)

    sourceSets {
        val jvmCommonMain by creating {
            dependsOn(commonMain.get())
        }

        jvmMain {
            dependsOn(jvmCommonMain)
        }

        androidMain {
            dependsOn(jvmCommonMain)
        }

        // Tests
        val jvmCommonTest by creating {
            dependsOn(jvmCommonMain)
            dependsOn(commonTest.get())
        }

        jvmTest {
            dependsOn(jvmMain.get())
            dependsOn(jvmCommonTest)
        }

        val androidUnitTest by getting {
            dependsOn(androidMain.get())
            dependsOn(jvmCommonTest)
        }
    }

    // =================================
    // JS Target

    val jsConfigure: KotlinJsTargetDsl.() -> Unit = {
        browser()
        nodejs()
        binaries.library()
    }

    js(IR, jsConfigure)

    sourceSets {
        jsTest {
            dependencies {
                api(kotlin("test"))
                api(kotlin("test-js"))
            }
        }
    }

    // =================================
    // Android Native Targets

    val androidNativeTargets = arrayOf(
        androidNativeArm32(),
        androidNativeArm64(),
        androidNativeX64(),
        androidNativeX86()
    )

    sourceSets {
        androidNativeMain {
            dependsOn(nativeMain.get())

            androidNativeTargets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        androidNativeTest {
            dependsOn(nativeTest.get())

            androidNativeTargets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }

    // =================================
    // Apple Targets (macOS required)

    val appleTargets = arrayOf(
        macosX64(),
        macosArm64(),

        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),

        watchosArm32(),
        watchosArm64(),
        watchosX64(),
        watchosSimulatorArm64(),

        tvosX64(),
        tvosArm64(),
        tvosSimulatorArm64()
    )

    sourceSets {
        appleMain {
            dependsOn(nativeMain.get())

            appleTargets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        appleTest {
            dependsOn(nativeTest.get())

            appleTargets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }

    // =================================
    // Linux targets

    val linuxTargets = arrayOf(
        linuxX64(),
        linuxArm64()
    )

    sourceSets {
        linuxMain {
            dependsOn(nativeMain.get())

            linuxTargets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        linuxTest {
            dependsOn(nativeTest.get())

            linuxTargets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }

    // =================================
    // Windows targets

    val mingwTargets = arrayOf(
        mingwX64()
    )

    sourceSets {
        linuxMain {
            mingwTargets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        linuxTest {
            mingwTargets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }

    // =================================
    // Web Assembly targets

    val wasmTargets = arrayOf<KotlinWasmTargetDsl>(
        wasmJs {
            jsConfigure()
            d8()
        },
        wasmWasi {
            nodejs()
        }
    )

    sourceSets {
        val wasmMain by creating {
            dependsOn(commonMain.get())

            wasmTargets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        val wasmTest by creating {
            dependsOn(commonTest.get())

            wasmTargets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }
}
