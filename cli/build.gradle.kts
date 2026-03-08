plugins {
    application
    alias(libs.plugins.kotlin.jvm)
}

application {
    applicationName = "inkui"
    mainClass.set("ink.ui.cli.MainKt")
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(25)
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.inkui.render.web.static)
}
