import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")

    id("maven-publish")
    id("signing")
}

version = "2.2.0"
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
        commonMain {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib-common")
            }
        }

        nativeMain {
            dependsOn(commonMain.get())
        }

        commonTest {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-test")
                api("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        nativeTest {
            dependsOn(commonTest.get())
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
        val jvmCommonMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
            }
        }

        jvmMain {
            dependsOn(jvmCommonMain)
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
            }
        }

        androidMain {
            dependsOn(jvmCommonMain)
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
                api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            }
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
        jsMain {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
                api("org.jetbrains.kotlin:kotlin-stdlib-js")
            }
        }

        jsTest {
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
    val targets = arrayOf(
        androidNativeArm32(),
        androidNativeArm64(),
        androidNativeX64(),
        androidNativeX86()
    )

    sourceSets {
        androidNativeMain {
            dependsOn(nativeMain.get())

            targets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        androidNativeTest {
            dependsOn(nativeTest.get())

            targets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }
}

// =================================
// Apple Targets (macOS required)
kotlin {
    val targets = arrayOf(
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

            targets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        appleTest {
            dependsOn(nativeTest.get())

            targets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }
}

// =================================
// Linux targets
kotlin {
    val targets = arrayOf(
        linuxX64(),
        linuxArm64()
    )

    sourceSets {
        linuxMain {
            dependsOn(nativeMain.get())

            targets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        linuxTest {
            dependsOn(nativeTest.get())

            targets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }
}

// =================================
// Windows targets
kotlin {
    val targets = arrayOf(
        mingwX64()
    )

    sourceSets {
        linuxMain {
            targets.forEach {
                getByName("${it.name}Main").dependsOn(this)
            }
        }

        linuxTest {
            targets.forEach {
                getByName("${it.name}Test").dependsOn(this)
            }
        }
    }
}

// =================================
// Web Assembly targets (NOT SUPPORTED. FOR NOW)
//@Suppress("OPT_IN_USAGE")
//kotlin {
//    val targets = arrayOf(
//        wasmJs(),
//        wasmWasi()
//    )
//
//    sourceSets {
//        val wasmMain by creating {
//            dependsOn(commonMain.get())
//
//            targets.forEach {
//                getByName("${it.name}Main").dependsOn(this)
//            }
//        }
//
//        val wasmTest by creating {
//            dependsOn(commonTest.get())
//
//            targets.forEach {
//                getByName("${it.name}Test").dependsOn(this)
//            }
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