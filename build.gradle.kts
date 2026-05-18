import java.time.temporal.ChronoUnit

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.nmcp.aggregation)
}

nmcpAggregation {
    centralPortal {
        username = providers.gradleProperty("sonatype.publish.user").getOrElse("")
        password = providers.gradleProperty("sonatype.publish.password").getOrElse("")

        publishingType = "USER_MANAGED"

        publicationName = "${property("GROUP")}:${property("VERSION")}"

        validationTimeout = java.time.Duration.of(30, ChronoUnit.MINUTES)

        uploadSnapshotsParallelism.set(1)
    }
}

dependencies {
    allprojects {
        nmcpAggregation(project(path))
    }
}

tasks.register("cleanProject").configure {
    delete(rootProject.buildDir)
}

tasks.register("buildSh", Exec::class).configure {
    commandLine("${projectDir.canonicalPath}/build.sh")
    workingDir = projectDir
}