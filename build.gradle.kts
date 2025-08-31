plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ru.practicum"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:7.0.2")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.assertj:assertj-core:3.27.0")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit-pioneer:junit-pioneer:2.3.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("ru.practicum.Main")
}

tasks.test {
    useJUnitPlatform()
}
