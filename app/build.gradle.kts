plugins {
    id("java")
    id("checkstyle")
    application
    jacoco
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

//    implementation ("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport { reports { xml.required.set(true) } }