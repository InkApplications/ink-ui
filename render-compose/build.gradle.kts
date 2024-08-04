plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("ink.publishing")
}

android {
    namespace = "com.inkapplications.ui"
    compileSdk = 34
}

kotlin {
    // Tier 1 w/o JS:
    jvmToolchain(11)
    jvm()
    androidTarget {
        publishAllLibraryVariants()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.components.resources)
            api(projects.structures)
        }
    }
}
