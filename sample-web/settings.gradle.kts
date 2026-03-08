pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "sample-web"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

includeBuild("../cli")
includeBuild("../sample-common")
includeBuild("../inkui-render-compose-html")
includeBuild("../inkui-render-web-common")
