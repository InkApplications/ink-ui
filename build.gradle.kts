import org.gradle.api.tasks.testing.logging.TestExceptionFormat

repositories {
    mavenCentral()
}

allprojects {
    repositories {
        mavenCentral()
    }
    tasks.withType(Test::class) {
        testLogging.exceptionFormat = TestExceptionFormat.FULL
    }
}
