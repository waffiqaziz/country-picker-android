import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.library)
  id("maven-publish")
}

android {
  namespace = "com.waffiq.countryPicker.countryPicker"
  compileSdk = 36

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
  kotlin {
    compilerOptions {
      jvmTarget = JvmTarget.JVM_1_8
    }
  }
  buildFeatures {
    viewBinding = true
  }

  testOptions {
    animationsDisabled = true
    unitTests.isReturnDefaultValues = true
    unitTests {
      isIncludeAndroidResources = true
    }
  }
  publishing {
    singleVariant("release") {
      withSourcesJar()
      withJavadocJar()
    }
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.recyclerview)

  // material3
  implementation(libs.google.material)

  // testing
  testImplementation(libs.junit)
  testImplementation(libs.robolectric)
  testImplementation(libs.mockito)
  testImplementation(libs.mockito.kotlin)
  testImplementation(libs.androidx.test.core)
}


afterEvaluate {
  publishing {
    publications {
      create<MavenPublication>("release") {
        from(components["release"])
        groupId = "com.github.waffiqaziz"
        artifactId = "country-picker"
        version = "0.1.4"
      }
    }
  }
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
