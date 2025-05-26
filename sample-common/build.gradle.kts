plugins {
    id("multiplatform.tier3")
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.structures)
        }
    }
}
