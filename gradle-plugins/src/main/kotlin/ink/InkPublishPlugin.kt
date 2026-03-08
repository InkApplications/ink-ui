package ink

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.jvm.tasks.Jar
import org.gradle.plugins.signing.Sign

class InkPublishPlugin: Plugin<Project>
{
    override fun apply(project: Project)
    {
        project.pluginManager.apply("maven-publish")
        project.pluginManager.apply("signing")
        project.pluginManager.apply("org.jetbrains.dokka")


        val dokkaTask = project.tasks.named("dokkaGenerate")

        val javadocJar = project.tasks.register("javadocJar", Jar::class.java) {
            dependsOn(dokkaTask)
            archiveClassifier.set("javadoc")
            from(project.layout.buildDirectory.dir("dokka/html"))
        }

        project.extensions.configure(org.gradle.api.publish.PublishingExtension::class.java) {
            repositories {
                val user = project.findProperty("mavenUser") as String?
                val pass = project.findProperty("mavenPassword") as String?
                if (user != null && pass != null) {
                    maven {
                        name = "MavenCentral"
                        setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                        credentials {
                            username = user
                            password = pass
                        }
                    }
                }
            }
            publications.withType(MavenPublication::class.java).configureEach {
                artifact(javadocJar)
                pom {
                    name.set("Ink-UI ${project.name}")
                    description.set("Branded UI Rendering components for KMP")
                    url.set("https://ui.inkapplications.com")
                    licenses {
                        license {
                            name.set("GPL-3.0")
                            url.set("https://www.gnu.org/licenses/gpl-3.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("reneevandervelde")
                            name.set("Renee Vandervelde")
                            email.set("Renee@InkApplications.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:https://github.com/InkApplications/ink-ui.git")
                        developerConnection.set("scm:git:ssh://git@github.com:InkApplications/ink-ui.git")
                        url.set("https://github.com/InkApplications/ink-ui")
                    }
                }
            }
        }

        project.extensions.configure(org.gradle.plugins.signing.SigningExtension::class.java) {
            val key = project.findProperty("signingKey") as String?
            val keyId = project.findProperty("signingKeyId") as String?
            val password = project.findProperty("signingPassword") as String?
            if (key != null && keyId != null && password != null) {
                useInMemoryPgpKeys(keyId, key, password)
                sign(project.extensions.getByType(org.gradle.api.publish.PublishingExtension::class.java).publications)
            }
        }

        val signingTasks = project.tasks.withType(Sign::class.java)
        project.tasks.withType(PublishToMavenRepository::class.java).configureEach {
            mustRunAfter(signingTasks)
        }
    }
}
