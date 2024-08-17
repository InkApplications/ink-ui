import org.jetbrains.kotlin.incremental.createDirectory

plugins {
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("multiplatform")
}

kotlin {
    js {
        browser()
        binaries.executable()
    }
    sourceSets {
        commonMain.dependencies {
            implementation(compose.html.core)
            implementation(compose.runtime)
            implementation(projects.renderComposeHtml)
        }
    }
}

tasks.register("buildStatic") {
    dependsOn(projects.renderStaticHtml.dependencyProject.tasks.named("installDist"))
    doLast {
        exec {
            val app = projects.renderStaticHtml.dependencyProject.layout.buildDirectory.get().file("install/render-ui/bin/render-ui")
            val script = project.layout.projectDirectory.file("src/staticMain/Sample.inkui.kts")
            val staticDir = project.layout.buildDirectory.get().dir("static").asFile
            if (!staticDir.exists()) {
                staticDir.createDirectory()
            }
            val output = "${staticDir.path}/sample.html"
            commandLine("sh", "-c", "$app $script > $output")
        }
    }
}
