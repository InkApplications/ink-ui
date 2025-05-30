plugins {
    application
    kotlin("jvm")
}

application {
    applicationName = "inkui"
    mainClass.set("ink.ui.cli.MainKt")
}

dependencies {
    implementation(projects.renderStaticHtml)
    implementation(projects.sampleCommon)
}
