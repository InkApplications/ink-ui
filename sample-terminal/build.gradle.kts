plugins {
    application
    kotlin("jvm")
}

application {
    applicationName = "sample-terminal"
    mainClass.set("example.MainKt")
}

dependencies {
    implementation(projects.renderTerminal)
}
