plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("inkPublish") {
            id = "com.inkapplications.gradle.ink.publish"
            implementationClass = "ink.InkPublishPlugin"
        }
    }
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.dokka)
}
