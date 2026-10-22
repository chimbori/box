plugins {
  alias(libs.plugins.android.application)
}

android {
  namespace = "com.chimbori.box"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.chimbori.box"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

dependencies {
  implementation(project(":shared"))
}
