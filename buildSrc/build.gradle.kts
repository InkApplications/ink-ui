plugins {
    `kotlin-dsl`
}
repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    jvmToolchain(11)
}
dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.android.gradle)
    implementation(libs.compose.gradle)
    implementation(libs.compose.compiler)
    implementation(libs.dokka)
    implementation(libs.kotlinx.binary.compatibility)
}
