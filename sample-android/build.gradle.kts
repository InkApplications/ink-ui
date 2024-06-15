plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("android")
}

android {
    namespace = "com.inkapplications.ui.example"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.inkapplications.ui.example"
        minSdk = 21
        targetSdk = 34
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":render-compose"))
    implementation(libs.androidx.activity)
    implementation(libs.activity.compose)
}
