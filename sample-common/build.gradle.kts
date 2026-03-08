plugins {
    alias(libs.plugins.kotlin.multiplatform)
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
    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.inkui.structures)
        }
    }
}
