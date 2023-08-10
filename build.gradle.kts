plugins {
    kotlin("jvm") version "1.8.20"
}

group = "com.github.numq"
version = "1.0.0"

repositories {
    mavenCentral()
}

val protobufVersion = findProperty("protobuf.version")
val schemaVersion = findProperty("schema.version")

dependencies {
    implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
    implementation("com.squareup.wire:wire-schema:$schemaVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}