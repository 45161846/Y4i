// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    kotlin("kapt") version "2.0.21"
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    alias(libs.plugins.compose.compiler)
    id("com.google.dagger.hilt.android") version "2.53.1" apply false
}