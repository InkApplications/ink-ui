plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("ink.publishing")
}
kotlin {
    js {
        browser()
        binaries.executable()
    }
    jvm()
    jvmToolchain(11)

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            implementation(compose.runtime)
            api(projects.structures)
        }
    }
}
