import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
}

version = "0.1.1"
group = "ru.pocketbyte.kydralogexample.common_lib"

val sampleLoggerVersion = "2.2.2"

android {
    compileSdk = properties["ANDROID_SDK_COMPILE"].toString().toInt()
    namespace = group.toString()

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
        getByName("release") {
            //
        }
    }
}

kotlin {
    androidTarget()
    jvmToolchain(11)

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
                api(libs.kotlin.stdlib)
                api("ru.pocketbyte.kydra:kydra-log:$sampleLoggerVersion")
            }
        }

        androidMain {
            dependencies {
                api(libs.kotlin.stdlib)
            }
        }

        jsMain {
            dependencies {
                api(libs.kotlin.stdlib)
            }
        }
    }
}

fun KotlinNativeTarget.configureAppleTarget() {
    binaries.framework {
        baseName = "KotlinCommon"
    }
}
