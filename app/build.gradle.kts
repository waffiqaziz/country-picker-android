plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.waffiq.countryPicker"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.waffiq.countryPicker"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  implementation(project(":countryPicker"))
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.recyclerview)
  implementation(libs.androidx.coordinatorlayout)

  implementation(libs.google.material)

  // testing
  testImplementation(libs.junit)
  testImplementation(libs.robolectric)
  testImplementation(libs.mockito)
  testImplementation(libs.mockito.kotlin)
//  testImplementation(libs.mock.io)
  testImplementation(libs.androidx.test.core)
//  testImplementation(libs.androidx.test.ext.junit)
//  testImplementation(libs.androidx.test.rules)
//
//  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.mockito.android)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.espresso)
}