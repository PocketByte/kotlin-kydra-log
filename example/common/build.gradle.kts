import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
}

version = "0.1.1"
group = "ru.pocketbyte.kydralogexample.common_lib"

val sampleLoggerVersion = "2.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

android {
    compileSdk = properties["ANDROID_SDK_COMPILE"].toString().toInt()
    namespace = group.toString()

    defaultConfig {
        minSdk = properties["ANDROID_SDK_MIN"].toString().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile(
                "${project.projectDir.absoluteFile}/src/androidMain/AndroidManifest.xml"
            )
        }
    }


    buildTypes {
        getByName("release") {
            //
        }
    }
}

kotlin {
    androidTarget()

    js(IR) {
        browser()
        binaries.library()
    }

    iosX64 { configureAppleTarget() }
    iosArm64 { configureAppleTarget() }
    iosSimulatorArm64 { configureAppleTarget() }

    watchosArm32 { configureAppleTarget() }
    watchosArm64 { configureAppleTarget() }
    watchosX64 { configureAppleTarget() }
    watchosSimulatorArm64 { configureAppleTarget() }

    tvosX64 { configureAppleTarget() }
    tvosArm64 { configureAppleTarget() }
    tvosSimulatorArm64 { configureAppleTarget() }

    sourceSets {
        // Main Source Sets
        commonMain {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib-common")
                api("ru.pocketbyte.kydra:kydra-log:$sampleLoggerVersion")
            }
        }

        androidMain {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
                api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            }
        }

        jsMain {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
                api("org.jetbrains.kotlin:kotlin-stdlib-js")
            }
        }
    }
}

fun KotlinNativeTarget.configureAppleTarget() {
    binaries.framework {
        baseName = "KotlinCommon"
    }
}
