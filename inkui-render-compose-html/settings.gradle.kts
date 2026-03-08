pluginManagement {
    includeBuild("../gradle-plugins")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "inkui-render-compose-html"

includeBuild("../inkui-structures")
includeBuild("../inkui-render-web-common")
