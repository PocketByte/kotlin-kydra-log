plugins {
    id("com.android.application")
    id("kotlin-android")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

android {
    compileSdk = properties["ANDROID_SDK_COMPILE"].toString().toInt()
    namespace = "ru.pocketbyte.kydralogexample"

    defaultConfig {
        applicationId = "ru.pocketbyte.kydralogexample"
        minSdk = properties["ANDROID_SDK_MIN"].toString().toInt()
        targetSdk = properties["ANDROID_SDK_TARGET"].toString().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            //
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("com.android.support.constraint:constraint-layout:2.0.4")

    implementation(project(":example:common"))
    implementation("ru.pocketbyte.kydra:kydra-log")
}
