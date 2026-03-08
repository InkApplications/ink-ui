plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

application {
    applicationName = "sample-terminal"
    mainClass.set("example.MainKt")
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(25)
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.inkui.sample.common)
    implementation(libs.inkui.render.terminal)
}
