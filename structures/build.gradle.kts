plugins {
    id("multiplatform.tier3")
    id("ink.publishing")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            api(libs.kotlinx.coroutines.core)
        }
    }
}
