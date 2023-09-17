plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("net.jqwik:jqwik:1.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("net.jqwik:jqwik:1.8.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
    annotationProcessor(project(":processor"))
    implementation(project(":serializator"))
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
