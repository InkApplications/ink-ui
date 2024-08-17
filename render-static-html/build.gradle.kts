plugins {
    application
    kotlin("jvm")
}

application {
    applicationName = "render-ui"
    mainClass.set("ink.ui.render.statichtml.MainKt")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlin.scripting.common)
    implementation(libs.kotlin.scripting.jvm.core)
    implementation(libs.kotlin.scripting.jvm.host)
    implementation(libs.kotlinx.html)
    api(projects.structures)
}
