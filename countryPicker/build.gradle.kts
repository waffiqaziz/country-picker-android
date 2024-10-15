plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  id("maven-publish")
}

android {
  namespace = "com.waffiq.countryPicker.countryPicker"
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

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

tasks.withType<Test> {
  reports {
    junitXml.required.set(true)  // Enable JUnit XML reports
    html.required.set(true)      // Enable HTML reports
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


afterEvaluate {
  publishing {
    publications {
      create<MavenPublication>("release") {
        from(components["release"])
        groupId = "com.github.waffiqaziz"
        artifactId = "country-picker"
        version = "0.0.2"
      }
    }
  }
}
