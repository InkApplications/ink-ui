plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("ink.publishing")
}

kotlin {
    // Tier 1 w/o JS:
    jvmToolchain(11)
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                api(projects.structures)
            }
        }
    }
}
