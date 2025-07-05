plugins {
    kotlin("plugin.serialization")
    id("multiplatform.tier3")
    id("ink.publishing")
}

kotlin {
    jvmToolchain(11)
    jvm()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            api(projects.structures)
            api(libs.bundles.ktor.client)
            api(libs.ktor.client.cio)
            api(libs.bundles.ktor.server)
        }
    }
}
