// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
}

tasks.register("cleanProject").configure {
    delete(rootProject.buildDir)
}

tasks.register("buildSh", Exec::class).configure {
    commandLine("${projectDir.canonicalPath}/build.sh")
    workingDir = projectDir
}