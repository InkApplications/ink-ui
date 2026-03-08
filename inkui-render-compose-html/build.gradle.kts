import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ink.publish)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

repositories {
    mavenCentral()
    google()
}

kotlin {
    js {
        browser()
    }
    abiValidation {
        @OptIn(ExperimentalAbiValidation::class)
        enabled.set(true)
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.html.core)
            implementation(libs.compose.runtime)
            implementation(libs.kotlinx.coroutines.core)
            api(libs.inkui.structures)
            api(libs.inkui.render.web.common)
        }
    }
}
