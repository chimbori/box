plugins {
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
  android {
    namespace = "com.chimbori.box.shared"
    compileSdk = 36
    minSdk = 24
  }

  listOf(iosArm64(), iosSimulatorArm64(), macosArm64()).forEach {
    it.binaries.framework {
      baseName = "Shared"
      isStatic = true
    }
  }

  js {
    browser()
    binaries.library()
    generateTypeScriptDefinitions()
  }
}
