import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.android.application.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
}

android {
  namespace = "io.getstream.webrtc.sample.compose"
  compileSdk = Configurations.compileSdk

  defaultConfig {
    applicationId = "io.getstream.webrtc.sample.compose"
    minSdk = Configurations.minSdk
    targetSdk = Configurations.targetSdk
    versionCode = Configurations.versionCode
    versionName = Configurations.versionName

    buildConfigField(
      "String",
      "SIGNALING_SERVER_IP_ADDRESS",
      "\"" + gradleLocalProperties(rootDir).getProperty("SIGNALING_SERVER_IP_ADDRESS", "") + "\""
    )
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
  }

  packagingOptions {
    resources {
      excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
  }

  lint {
    abortOnError = false
  }
}

dependencies {
  // compose
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.tooling)
  implementation(libs.androidx.compose.material)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.foundation.layout)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.constraintlayout)

  // image loading
  implementation(libs.landscapist.glide)

  // webrtc
  implementation(libs.webrtc)
  implementation(libs.okhttp.logging)

  // coroutines
  implementation(libs.kotlinx.coroutines.android)

  // logger
  implementation(libs.stream.log)
}