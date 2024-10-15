pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
  }
}

rootProject.name = "Country Picker"
include(":app")
include(":countryPicker")
