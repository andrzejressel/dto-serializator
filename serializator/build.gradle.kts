import java.nio.file.Files
import kotlin.io.path.createDirectories

plugins {
    `java-library`
    `maven-publish`
    jacoco
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
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val generateBoilerplate by tasks.registering {
    outputs.dir(layout.buildDirectory.dir("generated/sources/serializators"))
    doLast {

        val dir = outputs.files.single().toPath()
        val classDir = dir.resolve("pl/andrzejressel/sjs/serializator")
            .createDirectories()
        val alphabet = 'A'..'Z'

        (1..32).map { alphabet.take(it) }.forEachIndexed { _i, availableLetters ->
            val i = _i + 1
            val genericClasses = availableLetters
                .joinToString(separator = ", ")
            val arguments = availableLetters
                .joinToString(separator = ", ") { c -> "$c ${c.lowercaseChar()}" }
            val fields = availableLetters
                .map{c -> "public final $c ${c.lowercase()};" }
                .joinToString(separator = "\n") {"|  $it"}
            val constructorAssignments = availableLetters
                .map{c -> "this.${c.lowercase()} = ${c.lowercase()};" }
                .joinToString(separator = "\n") {"|    $it"}

            Files.createDirectories(dir)

            val clz = """
                |package pl.andrzejressel.sjs.serializator;
                
                |public class Tuple${i}<$genericClasses> {
                $fields
                |  public Tuple${i}(${arguments}) {
                    $constructorAssignments
                |  }
                |}
            """.trimMargin()

            Files.writeString(classDir.resolve("Tuple${i}.java"), clz)
        }

        (1..32).map { alphabet.take(it) }.forEachIndexed { _i, availableLetters ->
            val i = _i + 1
            val genericClasses = (availableLetters + listOf("RET"))
                .joinToString(separator = ", ")
            val deconstructorAssignments = availableLetters
                .map{c -> "var ${c.lowercase()} = serializators.${c.lowercase()}.serialize(deconstructed.${c.lowercase()});" }
                .joinToString(separator = "\n") {"|    $it"}
            val deconstructorAddToBbs = availableLetters
                .map { c -> "bbs.add(${c.lowercase()});" }
                .joinToString(separator = "\n") {"|    $it"}
            val relatedTuple = availableLetters.joinToString { c -> c.uppercase() }
            val serializatorsTuple = availableLetters.joinToString { c -> "Serializator<${c.uppercase()}>" }

            val deserializatonSteps = availableLetters
                .map { c -> "var ${c.lowercase()} = serializators.${c.lowercase()}.deserialize(bb);" }
                .joinToString(separator = "\n") {"|    $it"}

            Files.createDirectories(dir)

            val clz = """
                |package pl.andrzejressel.sjs.serializator;
                |
                |import org.jetbrains.annotations.NotNull;
                |
                |import java.nio.ByteBuffer;
                |import java.util.ArrayList;
                |import java.util.List;
                |import java.util.function.Function;
                |
                |public class Map${i}Serializator<$genericClasses> implements AppendSerializator<RET> {
                |
                |   private final Tuple${i}<$serializatorsTuple> serializators;
                |   private final Function<RET, Tuple${i}<$relatedTuple>> deconstruct;
                |   private final Function<Tuple${i}<$relatedTuple>, RET> construct;
                |
                |   public Map${i}Serializator(
                |     Tuple${i}<$serializatorsTuple> serializators, 
                |     Function<RET, Tuple${i}<$relatedTuple>> deconstruct, 
                |     Function<Tuple${i}<$relatedTuple>, RET> construct
                |   ) {
                |     this.serializators = serializators;
                |     this.deconstruct = deconstruct;
                |     this.construct = construct;
                |   }
                |
                |  @Override
                |  public @NotNull List<ByteBuffer> serializeToList(RET ret) {
                |    var deconstructed = deconstruct.apply(ret);
                     $deconstructorAssignments
                |    var bbs = new ArrayList<ByteBuffer>();
                     $deconstructorAddToBbs
                |    return bbs;
                |  }
                |    
                |  @Override
                |  public RET deserialize(ByteBuffer bb) {
                     $deserializatonSteps
                |    return construct.apply(new Tuple${i}<>(${availableLetters.joinToString {it.lowercase()}}));
                |   }
                |
                |}
            """.trimMargin()

            Files.writeString(classDir.resolve("Map${i}Serializator.java"), clz)
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(generateBoilerplate)
        }
    }
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
            url = uri("https://maven.pkg.github.com/andrzejressel/simple-java-serialization")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
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