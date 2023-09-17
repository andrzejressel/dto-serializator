import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    testImplementation("net.jqwik:jqwik-kotlin:1.8.0")
    kapt(project(":processor"))
    implementation(project(":serializator"))
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

// https://jqwik.net/docs/current/user-guide.html#build-configuration-for-kotlin
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xjsr305=strict", // Required for strict interpretation of
            "-Xemit-jvm-type-annotations" // Required for annotations on type variables
        )
        jvmTarget = "11" // 1.8 or above
        javaParameters = true // Required to get correct parameter names in reporting
    }
}