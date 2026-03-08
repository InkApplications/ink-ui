import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ink.publish)
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(25)
        vendor = JvmVendorSpec.ADOPTIUM
    }
    jvm()

    abiValidation {
        @OptIn(ExperimentalAbiValidation::class)
        enabled.set(true)
    }

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.mordant)
            api(libs.inkui.structures)
        }
    }
}
