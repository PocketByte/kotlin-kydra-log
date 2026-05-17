import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

plugins {
    id("maven-publish")
    id("signing")
}

val kydraPublishing = extensions.create<KydraPublishingExtension>("kydraPublishing")

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

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
        name.set(
            project.provider {
                targetName?.let { kydraPublishing.targetPomName(it) }
                    ?: kydraPublishing.pomName
            }
        )
        description.set(
            project.provider {
                targetName?.let { kydraPublishing.targetPomDescription(it) }
                    ?: kydraPublishing.pomDescription
            }
        )

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

// Root publication POM — deferred so kydraPublishing extension values are already set
afterEvaluate {
    publishing {
        publications.withType<MavenPublication> {
            artifact(javadocJar.get())
        }
        publications {
            named<MavenPublication>("kotlinMultiplatform") {
                configurePomDefault(pom, null)
            }
        }
    }
}

// Configure Target publications — mavenPublication blocks are evaluated lazily.
// Requires kydra-multiplatform to be applied first.
extensions.configure<KotlinMultiplatformExtension> {
    targets.forEach {
        val targetName = it.name.upperFirstChar()
        if (it is KotlinAndroidTarget) {
            afterEvaluate {
                it.mavenPublication {
                    val variant = if (this.artifactId.endsWith("debug")) {
                        "Debug"
                    } else {
                        "Release"
                    }
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
