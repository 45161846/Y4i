pluginManagement {
    plugins {
        id("com.android.application")
        id("org.jetbrains.kotlin.android")
        id("com.google.dagger.hilt.android")
        id("kotlin-kapt") version "2.0.0-rc2"
    }
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
    }
}


rootProject.name = "Russian"
include(":app")
 