plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
    java
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springframework.boot") version "3.2.4"
}

allprojects {
    group = "com.diagorn"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "io.spring.dependency-management")

    // Kotlin config
    kotlin {
        jvmToolchain(17)
    }

    // Для Spring - все классы должны быть open
    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
        annotation("org.springframework.stereotype.Component")
        annotation("org.springframework.stereotype.Service")
        annotation("org.springframework.stereotype.Repository")
        annotation("org.springframework.stereotype.Controller")
        annotation("org.springframework.web.bind.annotation.RestController")
        annotation("org.springframework.context.annotation.Configuration")
    }

    // Для JPA - no-arg конструкторы
    noArg {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.4")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.6")
        }

        // ВАЖНО: Явно объявите версию SpringDoc для всех модулей
        dependencies {
            dependency("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
            dependency("org.mapstruct:mapstruct:1.5.5.Final")
            dependency("org.mapstruct:mapstruct-processor:1.5.5.Final")
            dependency("org.projectlombok:lombok-mapstruct-binding:0.2.0")

            dependency("io.jsonwebtoken:jjwt-api:0.11.5")
            dependency("io.jsonwebtoken:jjwt-impl:0.11.5")
            dependency("io.jsonwebtoken:jjwt-jackson:0.11.5")
            dependency("commons-codec:commons-codec:1.16.0")
            dependency("org.liquibase:liquibase-core:4.4.2")
        }
    }

    dependencies {
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

// Dependency Management for all modules
configure(subprojects) {
    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.4")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.3")
        }
    }
}

// Common module
project(":common") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.mapstruct:mapstruct")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui")

        annotationProcessor("org.mapstruct:mapstruct-processor")
        annotationProcessor("org.projectlombok:lombok-mapstruct-binding")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

// Spring Boot modules
Module.SERVICES.forEach { module ->
    project(":${module.moduleName}") {
        apply(plugin = "org.springframework.boot")

        dependencies {
            implementation(project(":common"))
            implementation("org.springframework.boot:spring-boot-starter-web")
            testImplementation("org.springframework.boot:spring-boot-starter-test")
        }
    }
}

enum class Module(val moduleName: String) {
    AUTH("auth"),
    CHAT("chat"),
    COMMON("common"),
    EVENT("event"),
    GATEWAY("gw"),
    JUDGE("judge"),
    LINEBOARD("lineboard"),
    NOTIFICATION("notification"),
    SUBMISSION("submission"),
    TEAM("team");

    companion object {
        val ALL_MODULES = values().toSet()
        val LIBS = setOf(COMMON)
        val SERVICES = ALL_MODULES - COMMON
    }
}