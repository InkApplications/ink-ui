pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "sample-common"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

includeBuild("../inkui-structures")
