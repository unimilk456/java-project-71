import org.gradle.api.tasks.Exec

plugins {
    id("java")
    id("checkstyle")
    application
    jacoco
}

jacoco {
    toolVersion = "0.8.6"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}
group = "hexlet.code"
version = "1.0-SNAPSHOT"

application { mainClass.set("hexlet.code.App") }

repositories {
    mavenCentral()
}

dependencies {
    implementation ("info.picocli:picocli:4.7.5")
    implementation ("commons-cli:commons-cli:1.4")
    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.16.0")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("org.slf4j:slf4j-log4j12:2.0.12")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testRuntimeOnly ("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport { reports { xml.required.set(true) } }

tasks.test {
    finalizedBy("codeClimateTestReporter")
}

tasks.register<Exec>("codeClimateTestReporter") {
    dependsOn("test")
    // Ensure the path to cc-test-reporter is correct
    executable("./cc-test-reporter")

    doFirst {
        exec {
            commandLine("bash", "-c", "CC_TEST_REPORTER_ID=2c6466cc83db471db212472a08a2290d7a67c7c5b605ed886730e2a985a004ee ./cc-test-reporter before-build")
        }
    }

    doLast {
        exec {
            commandLine("bash", "-c","JACOCO_SOURCE_PATH=src/main/java CC_TEST_REPORTER_ID=2c6466cc83db471db212472a08a2290d7a67c7c5b605ed886730e2a985a004ee ./cc-test-reporter format-coverage build/reports/jacoco/test/jacocoTestReport.xml --input-type jacoco")
        }
        exec {
            commandLine("bash", "-c", "CC_TEST_REPORTER_ID=2c6466cc83db471db212472a08a2290d7a67c7c5b605ed886730e2a985a004ee ./cc-test-reporter upload-coverage")
        }
    }
}