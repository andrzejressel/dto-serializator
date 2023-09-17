import java.net.URI

plugins {
    kotlin("jvm")
    kotlin("kapt")
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    kapt("com.google.auto.service:auto-service:1.1.1")
    compileOnly("com.google.auto.service:auto-service:1.1.1")
    testImplementation(project(":serializator"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = mvnGroupId
            artifactId = mvnArtifactId
            version = mvnVersion
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI.create("https://maven.pkg.github.com/andrzejressel/simple-java-serialization")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}