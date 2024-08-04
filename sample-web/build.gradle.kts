plugins {
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("multiplatform")
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
            implementation(projects.renderComposeHtml)
        }
    }
}
