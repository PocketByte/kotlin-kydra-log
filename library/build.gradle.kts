import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")

    id("maven-publish")
    id("signing")
}

version = "2.0.0"
group = "ru.pocketbyte.kydra"

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
        getByName("debug") {
            //
        }
    }
}

// =================================
// Common Source Sets
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib-common")
            }
        }

        val commonTest by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-test")
                api("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        // Native common
        val nativeCommonMain by creating {
            dependsOn(commonMain)
        }
        val nativeCommonTest by creating {
            dependsOn(nativeCommonMain)
            dependsOn(commonTest)
        }
    }
}

// =================================
// JVM based targets
kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release", "debug")
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting

        val jvmCommonMain by creating {
            dependsOn(commonMain)
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
            }
        }

        val jvmMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
            }
        }

        val androidMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
                api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            }
        }

        configure(listOf(jvmMain, androidMain)) {
            dependsOn(jvmCommonMain)
        }

        // Tests
        val jvmCommonTest by creating {
            dependsOn(jvmCommonMain)
            dependsOn(commonTest)
        }

        val jvmTest by getting {
            dependsOn(jvmCommonTest)
            dependsOn(jvmMain)
        }

        val androidUnitTest by getting {
            dependsOn(jvmCommonTest)
            dependsOn(androidMain)
        }
    }
}

// =================================
// JS Target
kotlin {
    js(IR) {
        browser()
        nodejs()
        binaries.library()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
                api("org.jetbrains.kotlin:kotlin-stdlib-js")
            }
        }

        val jsTest by getting {
            dependsOn(jsMain)
            dependencies {
                api("org.jetbrains.kotlin:kotlin-test")
                api("org.jetbrains.kotlin:kotlin-test-js")
            }
        }
    }
}

// =================================
// Android Native Targets
kotlin {
    androidNativeArm32()
    androidNativeArm64()
    androidNativeX64()
    androidNativeX86()

    sourceSets {
        val androidNativeArm32Main by getting {
            dependsOn(getByName("nativeCommonMain"))
        }

        configure(listOf(
                getByName("androidNativeArm64Main"),
                getByName("androidNativeX64Main"),
                getByName("androidNativeX86Main")
        )) {
            dependsOn(androidNativeArm32Main)
        }

        val androidNativeArm32Test by getting {
            dependsOn(getByName("nativeCommonTest"))
        }

        configure(listOf(
                getByName("androidNativeArm64Test"),
                getByName("androidNativeX64Test"),
                getByName("androidNativeX86Test")
        )) {
            dependsOn(androidNativeArm32Test)
        }
    }
}

// =================================
// Apple Targets (macOS required)
kotlin {
    macosX64()
    macosArm64()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    watchosArm32()
    watchosArm64()
    watchosX64()
    watchosSimulatorArm64()

    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()

    sourceSets {
        val macosX64Main by getting {
            dependsOn(getByName("nativeCommonMain"))
        }

        configure(listOf(
            getByName("macosArm64Main"),
            getByName("iosX64Main"),
            getByName("iosArm64Main"),
            getByName("iosSimulatorArm64Main"),
            getByName("watchosArm32Main"),
            getByName("watchosArm64Main"),
            getByName("watchosX64Main"),
            getByName("watchosSimulatorArm64Main"),
            getByName("tvosArm64Main"),
            getByName("tvosX64Main"),
            getByName("tvosSimulatorArm64Main")
        )) {
            dependsOn(macosX64Main)
        }

        val macosX64Test by getting {
            dependsOn(getByName("nativeCommonTest"))
            dependsOn(macosX64Main)
        }

        configure(listOf(
            getByName("macosArm64Test"),
            getByName("iosX64Test"),
            getByName("iosArm64Test"),
            getByName("iosSimulatorArm64Test"),
            getByName("watchosArm32Test"),
            getByName("watchosArm64Test"),
            getByName("watchosX64Test"),
            getByName("watchosSimulatorArm64Test"),
            getByName("tvosArm64Test"),
            getByName("tvosX64Test"),
            getByName("tvosSimulatorArm64Test")
        )) {
            dependsOn(macosX64Test)
        }
    }
}

// =================================
// Linux targets
kotlin {
    linuxX64()
    linuxArm64()

    sourceSets {

        // =================================
        // Native based Source Sets
        val linuxX64Main by getting {
            dependsOn(getByName("nativeCommonMain"))
        }

        val linuxArm64Main by getting {
            dependsOn(linuxX64Main)
        }

        val linuxX64Test by getting {
            dependsOn(getByName("nativeCommonTest"))
            dependsOn(linuxX64Main)
        }

        val linuxArm64Test by getting {
            dependsOn(linuxX64Test)
        }
    }
}

// =================================
// Windows targets
kotlin {
    mingwX64() // Windows required

    sourceSets {
        val linuxX64Main by getting

        val mingwX64Main by getting {
            dependsOn(linuxX64Main)
        }

        val linuxX64Test by getting

        val mingwX64Test by getting {
            dependsOn(linuxX64Test)
        }
    }
}

// =================================
// Web Assembly targets (NOT SUPPORTED. FOR NOW)
//kotlin {
//    @Suppress("OPT_IN_USAGE")
//    wasm {
//        browser()
//        nodejs()
//        d8()
//    }
//
//    sourceSets {
//        getByName("wasmMain") {
//            dependsOn(getByName("commonMain"))
//        }
//        getByName("wasmTest") {
//            dependsOn(getByName("commonTest"))
//        }
//    }
//}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

//==================================================================================================
// Publication
//==================================================================================================

publishing {
    repositories {
        maven {
            name = "Sonatype"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            credentials {
                username = project.findProperty("sonatype.publish.user")?.toString() ?: ""
                password = project.findProperty("sonatype.publish.password")?.toString() ?: ""
            }
        }
    }
}

signing {
    sign(publishing.publications)
}

fun configurePomDefault(pom: MavenPom, targetName: String?) {
    pom.apply {
        if (targetName != null) {
            name.set("Kotlin Kydra Log (${targetName})")
            description.set("Kydra Log implementation for target \'${targetName}\'")
        } else {
            name.set("Kotlin Kydra Log")
            description.set("Kotlin Kydra Log - Kotlin Multiplatform Library that allows to write logs in common module. The way how logs will written defines for each platform independently.")
        }
        url.set("https://github.com/PocketByte/kotlin-kydra-log")
        issueManagement {
            url.set("https://github.com/PocketByte/kotlin-kydra-log/issues")
        }
        scm {
            url.set("https://github.com/PocketByte/kotlin-kydra-log.git")
        }
        developers {
            developer {
                organization.set("PocketByte")
                organizationUrl.set("pocketbyte.ru")
                email.set("mail@pocketbyte.ru")
            }
            developer {
                name.set("Denis Shurygin")
                email.set("sdi.linch@gmail.com")
            }
        }
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
    }
}

// Configure Root publication
publishing {
    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(javadocJar.get())
    }
    publications {
        val kotlinMultiplatform by getting {
            (this as? MavenPublication)?.apply {
                configurePomDefault(pom, null)
            }
        }
    }
}

// Configure Target publications
kotlin {
    targets.forEach {
        val targetName = name.upperFirstChar()
        if (it is KotlinAndroidTarget) {
            afterEvaluate {
                it.mavenPublication {
                    val variant = if (this.artifactId.endsWith("debug")) // FIXME
                    { "Debug"} else { "Release" }

                    configurePomDefault(pom, "$targetName $variant")
                }
            }
        } else {
            it.mavenPublication {
                configurePomDefault(pom, targetName)
            }
        }
    }
}

// Workaround for
// https://youtrack.jetbrains.com/issue/KT-46466/Kotlin-MPP-publishing-Gradle-7-disables-optimizations-because-of-task-dependencies
tasks.withType(Sign::class, configureAction = {
    val signingTask = this
    tasks.withType(AbstractPublishToMaven::class, configureAction = {
        this.dependsOn(signingTask)
    })
})


fun String.upperFirstChar(): String {
    if (this[0].isLowerCase()) {
        return this[0].uppercaseChar() + substring(1)
    }
    return this
}