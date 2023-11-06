plugins {
    kotlin("jvm")
    kotlin("kapt")
    `maven-publish`
    jacoco
    id("com.vanniktech.maven.publish")
}

repositories {
    mavenCentral()
}

dependencies {
    kapt("com.google.auto.service:auto-service:1.1.1")
    compileOnly("com.google.auto.service:auto-service:1.1.1")
    testImplementation(project(":serializator"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("com.google.testing.compile:compile-testing:0.21.0")
    implementation(project(":serializator"))
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val mvnGroupId = parent!!.group.toString()
val mvnArtifactId = name
val mvnVersion = parent!!.version.toString()


mavenPublishing {
    coordinates(mvnGroupId, mvnArtifactId, mvnVersion)
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
        html.required = false
    }
}

tasks.named("check") {
    dependsOn("jacocoTestReport")
}