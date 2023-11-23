// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.google.com")
        maven("https://plugins.gradle.org/m2/")

        // kotlin 1.3
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${properties["ANDROID_GRADLE"]}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${properties["KOTLIN_VERSION"]}")
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
    }
}


allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven ("https://kotlin.bintray.com/kotlinx")
        maven ("https://dl.bintray.com/kotlin/kotlin-eap") // kotlin 1.2
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

tasks.register("cleanProject").configure {
    delete(rootProject.buildDir)
}

tasks.register("buildSh", Exec::class).configure {
    commandLine("${projectDir.canonicalPath}/build.sh")
    workingDir = projectDir
}