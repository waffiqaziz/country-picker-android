plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  id ("maven-publish")
}

android {
  namespace = "com.waffiq.countryPicker"
  compileSdk = 34

  defaultConfig {
    minSdk = 23

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.cardview)
  implementation(libs.androidx.recyclerview)
  
  // material3
  implementation(libs.google.material)
}
afterEvaluate {
  publishing {
    publications {
      create<MavenPublication>("release") {
        from(components["release"])
        groupId = "com.github.waffiqaziz"
        artifactId = "country-picker"
        version = "0.0.1"
      }
    }
  }
}
