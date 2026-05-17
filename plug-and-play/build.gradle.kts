plugins {
    id("kydra-multiplatform")
    id("kydra-publish")
}

version = properties["VERSION"].toString()
group = properties["GROUP"].toString()

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":kydra-log-manual"))
            }
        }
    }
}

kydraPublishing {
    pomDescription = "Kotlin Kydra Log - Kotlin Multiplatform Library that allows to write logs in common module. The way how logs will written defines for each platform independently."
    targetPomDescription = { "Kydra Log implementation for target '$it'" }
}