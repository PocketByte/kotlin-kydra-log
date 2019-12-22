import org.gradle.api.publish.maven.MavenPom
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import ru.pocketbyte.kotlin.gradle.plugin.mpp_publish.*
import ru.pocketbyte.kotlin_mpp.plugin.publish.Publishing
import ru.pocketbyte.kotlin_mpp.plugin.publish.registerPlatformDependentPublishingTasks

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")

    id("maven-publish")
    id("com.github.dcendents.android-maven")
    id("signing")
}

version = LibraryInfo.version
group = LibraryInfo.group

android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion(BuildVersion.androidTool)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = project.version.toString()
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

kotlin {
    jvm()
    android {
        publishLibraryVariants("release", "debug")
    }
    js()

    macosX64() // macOS required
    iosX64()     // macOS required
    iosArm64() // macOS required
    iosArm32() // macOS required

    watchosArm32() // macOS required
    watchosArm64() // macOS required
    watchosX86()     // macOS required

    tvosArm64() // macOS required
    tvosX64()     // macOS required

    linuxX64()
    linuxArm64()
    linuxArm32Hfp()

    linuxMips32()     // Linux required
    linuxMipsel32() // Linux required

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX64()
    androidNativeX86()

    mingwX64() // Windows required
    mingwX86() // Windows required

    wasm32()

    sourceSets {
        // =================================
        // Main Source Sets
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib-common")
            }
        }

        val commonTest by getting {
            dependsOn(commonMain)
            dependencies {
                api("org.jetbrains.kotlin:kotlin-test")
                api("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        // =================================
        // JVM based Source Sets
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

        val androidTest by getting {
            dependsOn(jvmCommonTest)
            dependsOn(androidMain)
        }

        // =================================
        // JS Source Sets
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

        // =================================
        // Native based Source Sets
        val nativeCommonMain by creating {
            dependsOn(commonMain)
        }
        val nativeMain by creating
        val appleMain by creating
        val androidNativeMain by creating
        val mingwMain by creating

        configure(listOf(nativeMain, appleMain, androidNativeMain, mingwMain)) {
            dependsOn(nativeCommonMain)
        }

        configure(listOf(
                getByName("linuxX64Main"),
                getByName("linuxArm64Main"), getByName("linuxArm32HfpMain"),
                getByName("linuxMips32Main"), getByName("linuxMipsel32Main")
        )) {
            dependsOn(nativeMain)
        }

        configure(listOf(
                getByName("androidNativeArm32Main"), getByName("androidNativeArm64Main"),
                getByName("androidNativeX64Main"), getByName("androidNativeX86Main")
        )) {
            dependsOn(androidNativeMain)
        }

        configure(listOf(
                getByName("macosX64Main"),
                getByName("iosX64Main"),
                getByName("iosArm64Main"), getByName("iosArm32Main"),
                getByName("watchosX86Main"),
                getByName("watchosArm32Main"), getByName("watchosArm64Main"),
                getByName("tvosArm64Main"), getByName("tvosX64Main")
        )) {
            dependsOn(appleMain)
        }

        configure(listOf(
                getByName("mingwX64Main"), getByName("mingwX86Main")
        )) {
            dependsOn(nativeMain)
            dependsOn(mingwMain)
        }

        configure(listOf(getByName("wasm32Main"))) {
            dependsOn(commonMain)
        }

        //Tests

        val nativeCommonTest by creating {
            dependsOn(nativeCommonMain)
            dependsOn(commonTest)
        }
        val nativeTest by creating { dependsOn(nativeMain) }
        val appleTest by creating { dependsOn(appleMain) }
        val androidNativeTest by creating { dependsOn(androidNativeMain) }
        val mingwTest by creating { dependsOn(mingwMain) }

        configure(listOf(nativeTest, appleTest, androidNativeTest, mingwTest)) {
            dependsOn(nativeCommonTest)
        }

        configure(listOf(
                getByName("linuxX64Test"),
                getByName("linuxArm64Test"), getByName("linuxArm32HfpTest"),
                getByName("linuxMips32Test"), getByName("linuxMipsel32Test")
        )) {
            dependsOn(nativeTest)
        }

        configure(listOf(
                getByName("androidNativeArm32Test"), getByName("androidNativeArm64Test"),
                getByName("androidNativeX64Test"), getByName("androidNativeX86Test")
        )) {
            dependsOn(androidNativeTest)
        }

        configure(listOf(
                getByName("macosX64Test"),
                getByName("iosX64Test"),
                getByName("iosArm64Test"), getByName("iosArm32Test"),
                getByName("watchosX86Test"),
                getByName("watchosArm32Test"), getByName("watchosArm64Test"),
                getByName("tvosArm64Test"), getByName("tvosX64Test")
        )) {
            dependsOn(appleTest)
        }

        configure(listOf(
                getByName("mingwX64Test"), getByName("mingwX86Test")
        )) {
            dependsOn(nativeTest)
            dependsOn(mingwTest)
        }

        configure(listOf(getByName("wasm32Test"))) {
            dependsOn(commonTest)
        }
    }
}


//==================================================================================================
// Publication
//==================================================================================================

publishing {
    repositories {
        maven {
            name = "Bintray"
            url = uri("https://api.bintray.com/content/pocketbyte/kydra/${project.name}/${project.version}")
            credentials {
                username = project.findProperty("bintray.publish.user")?.toString() ?: ""
                password = project.findProperty("bintray.publish.apikey")?.toString() ?: ""
            }
        }
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

// Create Signing Tasks for Root
registerPomSigningTask(Publishing.ROOT_TARGET)
registerMetaSigningTask(Publishing.ROOT_TARGET)

// Create Signing Tasks for Targets
kotlin.targets.forEach { target ->
    when (target.platformType) {
        KotlinPlatformType.common, KotlinPlatformType.jvm, KotlinPlatformType.js -> {
            registerJarSigningTask(target.name)
            registerSourcesSigningTask(target.name)
            registerJavaDocSigningTask(target.name)
            registerPomSigningTask(target.name)
            registerMetaSigningTask(target.name)
        }
        KotlinPlatformType.native -> {
            registerKlibSigningTask(target.name,
                    target.compilations.getByName("main").output.allOutputs)
            registerSourcesSigningTask(target.name)
            registerJavaDocSigningTask(target.name)
            registerPomSigningTask(target.name)
            registerMetaSigningTask(target.name)

        }
        KotlinPlatformType.androidJvm -> {
            afterEvaluate {
                (target as? KotlinAndroidTarget)?.publishLibraryVariants?.forEach { variant ->
                    registerAarSigningTask(target.name, variant)
                    registerSourcesSigningTask(target.name, variant)
                    registerJavaDocSigningTask(target.name, variant)
                    registerPomSigningTask(target.name, variant)
                    registerMetaSigningTask(target.name, variant)
                }
            }
        }
    }
}

// Configure Root publication
publishing {
    publications {
        val kotlinMultiplatform by getting {
            (this as? MavenPublication)?.apply {
                addSigningsToPublication(this, Publishing.ROOT_TARGET)
                configurePomDefault(pom, null)
            }
        }
    }
}

// Configure Target publications
kotlin {
    configure(listOf(
            metadata(), jvm(), js(),
            macosX64(), iosX64(), iosArm64(), iosArm32(),
            watchosArm32(), watchosArm64(), watchosX86(),
            tvosArm64(), tvosX64(),
            linuxX64(), linuxArm64(), linuxArm32Hfp(),
            linuxMips32(), linuxMipsel32(),
            androidNativeArm32(), androidNativeArm64(),
            androidNativeX64(), androidNativeX86(),
            mingwX64(), mingwX86(), wasm32()
    )) {
        val targetName = name.upperFirstChar()
        mavenPublication {
            addSigningsToPublication(this, targetName)
            configurePomDefault(pom, targetName)
        }
    }
    android {
        val targetName = name.upperFirstChar()
        afterEvaluate {
            mavenPublication {
                val variant = if (this.artifactId.endsWith("debug")) // FIXME
                { "Debug"} else { "Release" }

                addSigningsToPublication(this, targetName, variant)
                configurePomDefault(pom, "$targetName $variant")
            }
        }
    }
}

registerPlatformDependentPublishingTasks()