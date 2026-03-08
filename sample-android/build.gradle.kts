import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.inkapplications.ui.example"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.inkapplications.ui.example"
        minSdk = 23
        targetSdk = 36
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(25)
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.inkui.render.compose.core)
    implementation(libs.androidx.activity)
    implementation(libs.activity.compose)
    implementation(libs.compose.foundation)
    implementation(libs.inkui.sample.common)
}

tasks.configureEach {
    if (name.startsWith("lint")) {
        enabled = false
    }
}
