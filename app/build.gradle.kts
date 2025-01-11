plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id ("org.jetbrains.kotlin.plugin.serialization")
    alias(libs.plugins.compose.compiler)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.russian"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.example.russian"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"

    ksp {
        arg("room.generateKotlin", "true")
    }
}

dependencies {
    // Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    //drag&drop
    implementation("org.burnoutcrew.composereorderable:reorderable:+")

    implementation("androidx.wear.compose:compose-material:1.4.0")
    implementation("androidx.work:work-runtime-ktx:2.9.1")
    val nav_version = "2.8.0"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")


    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)


    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.gson)
    implementation(libs.ktor.client.cio)


    implementation(libs.ktor.client.android)
//    implementation("io.ktor:ktor-client-darwin:3.0.0")
    implementation(kotlin("script-runtime"))
}