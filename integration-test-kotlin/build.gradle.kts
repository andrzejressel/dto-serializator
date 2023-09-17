plugins {
    kotlin("jvm")
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("net.jqwik:jqwik:1.8.0")
    implementation("org.jetbrains:annotations:24.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("net.jqwik:jqwik:1.8.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
    kapt(project(":processor"))
    implementation(project(":serializator"))
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
