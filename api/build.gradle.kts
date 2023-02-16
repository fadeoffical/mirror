/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/8.0/userguide/building_java_projects.html
 */

version = "0.0.1-alpha.0"
group = "fade"
description = "Reflections made easy!"

plugins {
    id("maven-publish")
    id("java-library")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    withJavadocJar()

    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Jar> {
    archiveBaseName.set(rootProject.name + "-" + project.name)
}

publishing {
    repositories {
        if (System.getenv().containsKey("CI_GITHUB")) maven {
            name = "github"
            url = uri("https://github.com/fadeoffical/mirror")
            credentials {
                username = System.getenv("CI_GITHUB_USERNAME")
                password = System.getenv("CI_GITHUB_PASSWORD")
            }
        }
    }
}
