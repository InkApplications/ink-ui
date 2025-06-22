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
            implementation(projects.sampleCommon)
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

private fun renderTo(input: String, output: String) {
    exec {
        val app = projects.cli.dependencyProject.layout.buildDirectory.get().file("install/inkui/bin/inkui")
        val script = project.layout.projectDirectory.file(input)
        commandLine("sh", "-c", "$app $script > $output")
    }
}

tasks.register("buildStatic") {
    dependsOn(projects.cli.dependencyProject.tasks.named("installDist"))
    doLast {
        staticOutputDir.mkdirs()
        renderTo("src/staticMain/index.inkui.kts", "${staticOutputDir.path}/index.html")
        renderTo("src/staticMain/typography.inkui.kts", "${staticOutputDir.path}/typography.html")
        renderTo("src/staticMain/elements.inkui.kts", "${staticOutputDir.path}/elements.html")
        copy {
            staticResDir.mkdirs()
            from(projects.renderWebCommon.dependencyProject.layout.projectDirectory.dir("src/commonMain/composeResources"))
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
        webDistDir.mkdirs()
        webDistComposeDir.mkdirs()

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
