# DTO Serializator
![GitHub release (with filter)](https://img.shields.io/github/v/release/andrzejressel/simple-java-serialization)

# Main points
- No external schema file - combination of Serializators is the schema
- No reflection

# Quick start

## Setting up Gradle

```kotlin
dependencies {
    implementation("pl.andrzejressel.dto:serializator:<version>")
    annotationProcessor("pl.andrzejressel.dto:processor:<version>")
}
```

## Serializing/deserializing

```java
import pl.andrzejressel.dto.serializator.GenerateSerializator;

@GenerateSerializator
class MyClass {
    ...
}


var serializator = MyClassSerializator.INSTANCE;
var serialized = serializator.serialize(new MyClass(...)).rewind();
var obj = serializator.deserialize(serialized);
```