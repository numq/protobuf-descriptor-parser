[![](https://jitpack.io/v/numq/protobuf-descriptor-parser.svg)](https://jitpack.io/#numq/protobuf-descriptor-parser)

# Protobuf Descriptor Parser

A library that provides extension functions for parsing the basic descriptors of *Protocol Buffers*, which allows you to
create descriptors without code compilation, which is especially useful when working with dynamic messages.

## Descriptors

You can change any parsing method by calling `toBuilder()`

| Wire Schema<br/>class name | Protobuf Kotlin<br/>class name | Method name              |
|----------------------------|--------------------------------|--------------------------|
| EnumConstantElement        | EnumValueDescriptorProto       | enumValueDescriptorProto |
| EnumElement                | EnumDescriptorProto            | enumDescriptorProto      |
| FieldElement               | FieldDescriptorProto           | fieldDescriptorProto     |
| MessageElement             | DescriptorProto                | descriptorProto          |
| ProtoFileElement           | FileDescriptorProto            | fileDescriptorProto      |
| RpcElement                 | MethodDescriptorProto          | methodDescriptorProto    |
| ServiceElement             | ServiceDescriptorProto         | serviceDescriptorProto   |

## Dependencies

> You can exclude dependencies if they are already in use

### [Protobuf Kotlin](https://mvnrepository.com/artifact/com.google.protobuf/protobuf-kotlin)

[Documentation](https://www.javadoc.io/doc/com.google.protobuf/protobuf-kotlin/3.23.4/com/google/protobuf/package-summary.html)
**- v3.23.4**

### [Wire Schema](https://mvnrepository.com/artifact/com.squareup.wire/wire-schema)

[Documentation](https://square.github.io/wire/2.x/wire-schema/index.html?com/squareup/wire/schema/Schema.html) **-
v4.8.0**

## Installation

### build.gradle

```groovy
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }

    dependencies {
        implementation('com.github.numq:protobuf-descriptor-parser:1.0.0') {
            exclude group: 'com.google.protobuf', module: 'protobuf-kotlin'
            exclude group: 'com.squareup.wire', module: 'wire-schema'
        }
    }
```

### build.gradle.kts

```kotlin
    allprojects {
        repositories {
            maven("https://jitpack.io")
        }
    }
    
    dependencies {
        implementation("com.github.numq:protobuf-descriptor-parser:1.0.0") {
            exclude(group = "com.google.protobuf", module = "protobuf-kotlin")
            exclude(group = "com.squareup.wire", module = "wire-schema")
        }
    }
```

## Usage

```kotlin
val protoFile = File("./path/to/*.proto")
val schema = ProtoParser.parse(Location.get(path), proto.readText())
val descriptor = Descriptors.FileDescriptor.buildFrom(schema.parseDescriptor(protoFile.Name), arrayOf())
// Use descriptor to create a dynamic message or for other purposes
```