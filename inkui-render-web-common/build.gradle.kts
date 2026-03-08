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
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(25)
        vendor = JvmVendorSpec.ADOPTIUM
    }
    js {
        browser()
    }
    jvm()

    abiValidation {
        @OptIn(ExperimentalAbiValidation::class)
        enabled.set(true)
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            implementation(libs.compose.runtime)
            api(libs.inkui.structures)
        }
    }
}
