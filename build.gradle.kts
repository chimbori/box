plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.kotlin.multiplatform.library) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
}

val webAppDir = layout.projectDirectory.dir("webApp")

val npmInstallWeb by tasks.registering(Exec::class) {
  dependsOn(":shared:jsBrowserProductionLibraryDistribution")
  workingDir(webAppDir)
  commandLine("npm", "install")
}

tasks.register<Exec>("webDev") {
  dependsOn(npmInstallWeb)
  workingDir(webAppDir)
  commandLine("npm", "run", "dev")
}
