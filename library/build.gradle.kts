plugins {
    id("kydra-multiplatform")
    id("com.gradleup.nmcp")
    id("kydra-publish")
}

version = properties["VERSION"].toString()
group = properties["GROUP"].toString()

kydraPublishing {
    pomName = "Kotlin Kydra Log for manual DI"
    pomDescription = "Kotlin Kydra Log - Kotlin Multiplatform Library that allows to write logs in common module. The way how logs will written defines for each platform independently. Version for manual DI"
    targetPomDescription = { "Kydra Log implementation (manual DI) for target '$it'" }
}