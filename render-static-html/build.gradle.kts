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
            api(libs.kotlin.scripting.common)
            implementation(libs.kotlin.scripting.jvm.core)
            implementation(libs.kotlin.scripting.jvm.host)
            implementation(libs.kotlinx.html)
            api(projects.structures)
            api(projects.renderWebCommon)
        }
    }
}
