import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.20"
}

group = "dev.cwenck"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.3")
    implementation("com.discord4j:discord4j-core:3.1.6")
    testImplementation(kotlin("test"))
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "core.BotKt"
    }

    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
}

tasks.test {
    useTestNG()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
