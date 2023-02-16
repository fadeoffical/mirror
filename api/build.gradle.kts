/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/8.0/userguide/building_java_projects.html
 */

version = "0.0.2-beta.0"
group = "fade"
description = "Reflections made easy!"

if (System.getenv().containsKey("CI_GITHUB")) {
    val branchName = System.getenv("CI_GITHUB_BRANCH")
    if (branchName == "develop") version = "${(version as String)}+$branchName" // this is a bit stupid but whatever
}

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

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

publishing {
    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/FADEOffical/mirror")
            credentials {
                username = System.getenv("CI_GITHUB_USERNAME")
                password = System.getenv("CI_GITHUB_PASSWORD")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            artifactId = rootProject.name
        }
    }
}
