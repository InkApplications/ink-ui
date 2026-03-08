plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

repositories {
    mavenCentral()
    google()
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(25)
        vendor = JvmVendorSpec.ADOPTIUM
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.inkui.sample.common)
            implementation(libs.compose.html.core)
            implementation(libs.compose.runtime)
            implementation(libs.inkui.render.compose.html)
        }
    }
}

val staticOutputDir = project.layout.buildDirectory.get().dir("static").asFile
val staticResDir = project.layout.buildDirectory.get().dir("static/res").asFile
val composeOutputDir = project.layout.buildDirectory.get().dir("dist/js/productionExecutable").asFile
val webDistDir = project.layout.buildDirectory.get().dir("dist/web").asFile
val webDistComposeDir = project.layout.buildDirectory.get().dir("dist/web/compose").asFile

private fun renderTo(input: String, output: String) {
    val app = gradle.includedBuild("cli").projectDir.resolve("build/install/inkui/bin/inkui")
    val script = project.layout.projectDirectory.file(input)
    val process = ProcessBuilder("sh", "-c", "$app $script > \"$output\"")
        .inheritIO()
        .start()
    process.waitFor()
}

tasks.register("buildStatic") {
    dependsOn(gradle.includedBuild("cli").task(":installDist"))
    doLast {
        staticOutputDir.mkdirs()
        renderTo("src/staticMain/index.inkui.kts", "${staticOutputDir.path}/index.html")
        renderTo("src/staticMain/typography.inkui.kts", "${staticOutputDir.path}/typography.html")
        renderTo("src/staticMain/elements.inkui.kts", "${staticOutputDir.path}/elements.html")
        copy {
            staticResDir.mkdirs()
            from(gradle.includedBuild("inkui-render-web-common").projectDir.resolve("src/commonMain/composeResources"))
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
