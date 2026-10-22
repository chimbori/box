plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.kotlin.multiplatform.library) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.serialization) apply false
}

val webAppDir = layout.projectDirectory.dir("webApp")

// Gradle does not always inherit the shell PATH, so Homebrew's npm may be missing.
val webPath = listOf("/opt/homebrew/bin", "/usr/local/bin", System.getenv("PATH") ?: "").joinToString(":")

val npmInstallWeb by tasks.registering(Exec::class) {
  dependsOn(":shared:jsBrowserProductionLibraryDistribution")
  workingDir(webAppDir)
  environment("PATH", webPath)
  commandLine("npm", "install")
}

tasks.register<Exec>("webDev") {
  dependsOn(npmInstallWeb)
  workingDir(webAppDir)
  environment("PATH", webPath)
  commandLine("npm", "run", "dev")
}
