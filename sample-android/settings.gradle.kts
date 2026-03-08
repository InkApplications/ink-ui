pluginManagement {
    includeBuild("../gradle-plugins")

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

rootProject.name = "sample-android"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

includeBuild("../inkui-render-compose")
includeBuild("../sample-common")

