enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "inkui"

includeBuild("gradle-plugins")
includeBuild("cli")
includeBuild("inkui-render-compose")
includeBuild("inkui-render-web-common")
includeBuild("inkui-render-compose-html")
includeBuild("inkui-render-remote")
includeBuild("inkui-render-web-static")
includeBuild("inkui-render-terminal")
includeBuild("inkui-structures")
includeBuild("sample-android")
includeBuild("sample-common")
includeBuild("sample-terminal")
includeBuild("sample-web")
