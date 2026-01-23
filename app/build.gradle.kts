import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.application)
}

android {
  namespace = "com.waffiq.countryPicker"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.waffiq.countryPicker"
    minSdk = 24
    targetSdk = 36
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
  kotlin {
    compilerOptions {
      jvmTarget = JvmTarget.JVM_1_8
    }
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
  implementation(libs.constraintlayout)

  // testing
  testImplementation(libs.junit)
  testImplementation(libs.robolectric)
  testImplementation(libs.mockito)
  testImplementation(libs.mockito.kotlin)
  testImplementation(libs.androidx.test.core)

  androidTestImplementation(libs.core.ktx)
  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.test.ext.junit)

  implementation(libs.androidx.espresso.idling.resource)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.espresso.contrib)

  testImplementation(libs.mockito.inline)
  androidTestImplementation(libs.mockito.android)
//  androidTestImplementation("androidx.test:rules:1.6.1")

//  androidTestImplementation("com.android.support.test.espresso:espresso-contrib:3.0.2")
//  androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
}

// Check if the test task exists
tasks.withType<Test>().configureEach {
  testLogging {
    events("passed", "skipped", "failed")
    showExceptions = true
    showCauses = true
    showStackTraces = true
    exceptionFormat = TestExceptionFormat.FULL // Shows full stack trace
    // This will show the names of the tests being executed
    showStandardStreams = true
  }
}