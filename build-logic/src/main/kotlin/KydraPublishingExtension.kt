open class KydraPublishingExtension {
    var pomName: String = "Kotlin Kydra Log"
    var pomDescription: String = ""
    var targetPomName: (targetName: String) -> String = { "$pomName ($it)" }
    var targetPomDescription: (targetName: String) -> String = { pomDescription }
}
