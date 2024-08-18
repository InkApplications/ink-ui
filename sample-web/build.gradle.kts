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

val staticOutputDir = project.layout.buildDirectory.get().dir("static").asFile
val staticResDir = project.layout.buildDirectory.get().dir("static/res").asFile
val composeOutputDir = project.layout.buildDirectory.get().dir("dist/js/productionExecutable").asFile
val webDistDir = project.layout.buildDirectory.get().dir("dist/web").asFile
val webDistComposeDir = project.layout.buildDirectory.get().dir("dist/web/compose").asFile

tasks.register("buildStatic") {
    dependsOn(projects.renderStaticHtml.dependencyProject.tasks.named("installDist"))
    doLast {
        staticOutputDir.createDirectory()
        exec {
            val app = projects.renderStaticHtml.dependencyProject.layout.buildDirectory.get().file("install/render-ui/bin/render-ui")
            val script = project.layout.projectDirectory.file("src/staticMain/Sample.inkui.kts")
            val output = "${staticOutputDir.path}/index.html"
            commandLine("sh", "-c", "$app $script > $output")
        }
        copy {
            staticResDir.createDirectory()
            from(projects.renderComposeHtml.dependencyProject.layout.projectDirectory.dir("src/commonMain/composeResources"))
            into(staticResDir)
        }
    }
}

tasks.named("assemble") {
    dependsOn("buildStatic")
}

tasks.register("distWeb") {
    dependsOn("assemble")

    doLast {
        webDistDir.createDirectory()
        webDistComposeDir.createDirectory()

        copy {
            from(staticOutputDir)
            into(webDistDir)
        }
        copy {
            from(composeOutputDir)
            into(webDistComposeDir)
        }
    }
}
