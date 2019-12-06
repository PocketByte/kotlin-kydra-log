package ru.pocketbyte.kotlin.gradle.plugin.mpp_publish

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.register
import org.gradle.plugins.signing.Sign
import ru.pocketbyte.kotlin_mpp.plugin.publish.visitPublishingTasks

object Signing {
    const val GROUP = "signing"
    val REPOS_WITH_SIGNINGS = setOf("Sonatype")
}


// Signing Tasks =====================

fun pomSigningTaskName(targetName: String): String {
    return "sign${targetName.upperFirstChar()}Pom"
}

fun Project.pomSigningTask(targetName: String): Sign? {
    return tasks.findByName(pomSigningTaskName(targetName)) as? Sign
}

fun metaSigningTaskName(targetName: String): String {
    return "sign${targetName.upperFirstChar()}Meta"
}

fun Project.metaSigningTask(targetName: String): Sign? {
    return tasks.findByName(metaSigningTaskName(targetName)) as? Sign
}

fun sourcesSigningTaskName(targetName: String): String {
    return "sign${targetName.upperFirstChar()}Sources"
}

fun Project.sourcesSigningTask(targetName: String): Sign? {
    return tasks.findByName(sourcesSigningTaskName(targetName)) as? Sign
}

fun javaDocSigningTaskName(targetName: String): String {
    return "sign${targetName.upperFirstChar()}JavaDoc"
}

fun Project.javaDocSigningTask(targetName: String): Sign? {
    return tasks.findByName(javaDocSigningTaskName(targetName)) as? Sign
}

fun jarSigningTaskName(targetName: String): String {
    return "sign${targetName.upperFirstChar()}Jar"
}

fun Project.jarSigningTask(targetName: String): Sign? {
    return tasks.findByName(jarSigningTaskName(targetName)) as? Sign
}

fun klibSigningTaskName(targetName: String): String {
    return "sign${targetName.upperFirstChar()}Klib"
}

fun Project.klibSigningTask(targetName: String): Sign? {
    return tasks.findByName(klibSigningTaskName(targetName)) as? Sign
}

fun aarSigningTaskName(targetName: String, variantName: String): String {
    return "sign${targetName.upperFirstChar()}${variantName.upperFirstChar()}Aar"
}

fun Project.aarSigningTask(targetName: String, variantName: String): Sign? {
    return tasks.findByName(aarSigningTaskName(targetName, variantName)) as? Sign
}


// Tasks for signing ==============

fun Project.pomTaskForSigning(targetName: String): Task? {
    return project.tasks.findByName("generatePomFileFor${targetName.upperFirstChar()}Publication")
}

fun Project.metaTaskForSigning(targetName: String): Task? {
    return project.tasks.findByName("generateMetadataFileFor${targetName.upperFirstChar()}Publication")
}

fun Project.sourcesTaskForSigning(targetName: String): Task? {
    return project.tasks.findByName("${targetName.lowerFirstChar()}SourcesJar")
}

fun Project.javaDocTaskForSigning(targetName: String): Task? {
    return project.tasks.findByName("${targetName.lowerFirstChar()}JavaDoc")
}

fun Project.jarTaskForSigning(targetName: String): Task? {
    return project.tasks.findByName("${targetName.lowerFirstChar()}Jar")
}

fun Project.klibTaskForSigning(targetName: String): Task? {
    return project.tasks.findByName("compileKotlin${targetName.upperFirstChar()}")
}

fun Project.aarTaskForSigning(variantName: String): Task? {
    return project.tasks.findByName("bundle${variantName.upperFirstChar()}Aar")
}


// Signing tasks registration ==================

fun Project.registerPomSigningTask(targetName: String): TaskProvider<Sign>? {
    return registerSigningTask(
        targetName,
        pomTaskForSigning(targetName),
        pomSigningTaskName(targetName)
    )
}

fun Project.registerPomSigningTask(targetName: String, variantName: String): TaskProvider<Sign>? {
    return registerPomSigningTask("${targetName}${variantName.upperFirstChar()}")
}

fun Project.registerMetaSigningTask(targetName: String): TaskProvider<Sign>? {
    return registerSigningTask(
        targetName,
        metaTaskForSigning(targetName),
        metaSigningTaskName(targetName)
    )
}

fun Project.registerMetaSigningTask(targetName: String, variantName: String): TaskProvider<Sign>? {
    return registerMetaSigningTask("${targetName}${variantName.upperFirstChar()}")
}

fun Project.registerSourcesSigningTask(targetName: String): TaskProvider<Sign>? {
    return registerJarSigningTask(
        targetName,
        sourcesTaskForSigning(targetName),
        sourcesSigningTaskName(targetName)
    )
}

fun Project.registerSourcesSigningTask(targetName: String, variantName: String): TaskProvider<Sign>? {
    return registerSourcesSigningTask("${targetName}${variantName.upperFirstChar()}")
}

fun Project.registerJavaDocSigningTask(targetName: String): TaskProvider<Sign>? {
    return registerJarSigningTask(
            targetName,
            javaDocTaskForSigning(targetName),
            javaDocSigningTaskName(targetName)
    )
}

fun Project.registerJavaDocSigningTask(targetName: String, variantName: String): TaskProvider<Sign>? {
    return registerJavaDocSigningTask("${targetName}${variantName.upperFirstChar()}")
}

fun Project.registerJarSigningTask(targetName: String): TaskProvider<Sign>? {
    return registerJarSigningTask(
        targetName,
        jarTaskForSigning(targetName),
        jarSigningTaskName(targetName)
    )
}

fun Project.registerKlibSigningTask(targetName: String, filesToSign: FileCollection): TaskProvider<Sign>? {
    val file = filesToSign.files.find {
        it.extension == "klib"
    }
    return if (file != null) {
        klibTaskForSigning(targetName)?.let {
            registerSigningTask(
                targetName,
                tasks.register(klibSigningTaskName(targetName), Sign::class) {
                    group = Signing.GROUP
                    sign(file)
                    dependsOn(it)
                }
            )
        }
    } else {
        null
    }
}

fun Project.registerAarSigningTask(targetName: String, variantName: String): TaskProvider<Sign>? {
    return registerSigningTask(
        "${targetName}${variantName.upperFirstChar()}",
        aarTaskForSigning(variantName),
        aarSigningTaskName(targetName, variantName)
    )
}


private fun Project.registerSigningTask(targetName: String, taskToSign: Task?, signingTaskName: String): TaskProvider<Sign>? {
    return taskToSign?.let {
        registerSigningTask(
            targetName,
            tasks.register(signingTaskName, Sign::class) {
                group = Signing.GROUP
                sign(*it.outputs.files.files.toTypedArray())
                dependsOn(it)
            }
        )
    }
}

private fun Project.registerJarSigningTask(targetName: String, taskToSign: Task?, signingTaskName: String): TaskProvider<Sign>? {
    return taskToSign?.let {
        registerSigningTask(
            targetName,
            tasks.register(signingTaskName, Sign::class) {
                group = Signing.GROUP
                sign(taskToSign)
                dependsOn(it)
            }
        )
    }
}

private fun Project.registerSigningTask(targetName: String, signingTask: TaskProvider<Sign>?): TaskProvider<Sign>? {
    return signingTask?.apply {
        visitPublishingTasks(targetName) { task, repoName ->
            if (Signing.REPOS_WITH_SIGNINGS.contains(repoName)) {
                task.dependsOn(signingTask)
            }
        }
    }
}



fun Project.addSigningsToPublication(publication: MavenPublication, targetName: String) {
    pomSigningTask(targetName)?.let {
        it.signatureFiles.find { file ->
            file.name == "pom-default.xml.asc"
        }?.let { file ->
            publication.artifact(file) {
                classifier = null
                extension = "pom.asc"
            }
        }
    }
    metaSigningTask(targetName)?.let {
        it.signatureFiles.find { file ->
            file.name == "module.json.asc"
        }?.let { file ->
            publication.artifact(file) {
                classifier = null
                extension = "module.asc"
            }
        }
    }
    sourcesSigningTask(targetName)?.let {
        it.signatureFiles.find { file ->
            file.name.endsWith("sources.jar.asc")
        }?.let { file ->
            publication.artifact(file) {
                classifier = "sources"
                extension = "jar.asc"
            }
        }
    }
    javaDocSigningTask(targetName)?.let {
        it.signatureFiles.find { file ->
            file.name.endsWith("javadoc.jar.asc")
        }?.let { file ->
            publication.artifact(file) {
                classifier = "javadoc"
                extension = "jar.asc"
            }
        }
    }
    jarSigningTask(targetName)?.let {
        it.signatureFiles.find { file ->
            file.name.endsWith("jar.asc")
        }?.let { file ->
            publication.artifact(file) {
                classifier = null
                extension = "jar.asc"
            }
        }
    }
    klibSigningTask(targetName)?.let {
        it.signatureFiles.find { file ->
            file.name.endsWith("klib.asc")
        }?.let { file ->
            publication.artifact(file) {
                classifier = null
                extension = "klib.asc"
            }
        }
    }
}

fun Project.addSigningsToPublication(publication: MavenPublication, targetName: String, variantName: String) {
    addSigningsToPublication(publication, "${targetName}${variantName.upperFirstChar()}")

    aarSigningTask(targetName, variantName)?.let {
        it.signatureFiles.find { file ->
            file.name.endsWith("aar.asc")
        }?.let { file ->
            publication.artifact(file) {
                classifier = null
                extension = "aar.asc"
            }
        }
    }
}