pluginManagement {
    includeBuild("../gradle-plugins")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "inkui-render-web-static"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

includeBuild("../inkui-structures")
includeBuild("../inkui-render-web-common")
