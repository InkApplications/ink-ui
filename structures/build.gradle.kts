plugins {
    id("multiplatform.tier3")
    id("ink.publishing")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
        }
    }
}
