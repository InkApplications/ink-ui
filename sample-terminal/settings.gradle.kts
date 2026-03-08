pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "sample-terminal"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

includeBuild("../sample-common")
includeBuild("../inkui-render-terminal")
