# Simple Java Serialization (SJS)
![GitHub release (with filter)](https://img.shields.io/github/v/release/andrzejressel/simple-java-serialization)

# Main points
- No external schema file - combination of Serializators is the schema
- No reflection

# Quick start

## Setting up Gradle

### Kotlin

```kotlin
plugins {
    kotlin("kapt")
}

repositories {
    maven {
        name = "SJS"
        url = uri("https://maven.pkg.github.com/andrzejressel/sjs")
    }
}

dependencies {
    implementation("pl.andrzejressel.sjs:serializator:<version>")
    kapt("pl.andrzejressel.sjs:processor:<version>") // When using Kotlin
}
```
### Java

```kotlin
repositories {
    maven {
        name = "SJS"
        url = uri("https://maven.pkg.github.com/andrzejressel/sjs")
    }
}

dependencies {
    implementation("pl.andrzejressel.sjs:serializator:<version>")
    annotationProcessor("pl.andrzejressel.sjs:processor:<version>") // When using Java
}
```

## Serializing/deserializing

```java
import pl.andrzejressel.sjs.serializator.GenerateSerializator;

@GenerateSerializator
class MyClass {
    ...
}


var serializator = MyClassSerializator.INSTANCE;
var serialized = serializator.serializeFlatten(new MyClass(...));
// If you want to reuse `serialized` bytebuffer you have to call `flip()` on it
var obj = serializator.deserialize(serialized);
```