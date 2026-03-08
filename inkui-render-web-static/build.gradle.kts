import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ink.publish)
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
    jvm()

    abiValidation {
        @OptIn(ExperimentalAbiValidation::class)
        enabled.set(true)
    }

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            api(libs.kotlin.scripting.common)
            implementation(libs.kotlin.scripting.jvm.core)
            implementation(libs.kotlin.scripting.jvm.host)
            implementation(libs.kotlinx.html)
            api(libs.inkui.structures)
            api(libs.inkui.render.web.common)
        }
    }
}
