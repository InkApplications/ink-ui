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
    sourceSets {
        commonMain.dependencies {
            implementation(compose.html.core)
            implementation(compose.runtime)
            implementation(libs.kotlinx.coroutines.core)
            api(projects.structures)
            api(projects.renderWebCommon)
        }
    }
}
