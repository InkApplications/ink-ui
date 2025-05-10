plugins {
    kotlin("multiplatform")
    id("ink.publishing")
}

kotlin {
    jvmToolchain(11)
    jvm()
    sourceSets {
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.mordant)
            api(projects.structures)
        }
    }
}
