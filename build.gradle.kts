// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    kotlin("kapt") version "1.9.24"
    id("com.google.devtools.ksp") version "1.9.24-1.0.20"
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}