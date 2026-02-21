plugins {
    java
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springframework.boot") version "3.2.4" apply false
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

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.4")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")
        }

        dependencies {
            dependency("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
            dependency("org.mapstruct:mapstruct:1.5.5.Final")
            dependency("org.mapstruct:mapstruct-processor:1.5.5.Final")
            dependency("org.projectlombok:lombok-mapstruct-binding:0.2.0")
            dependency("io.jsonwebtoken:jjwt-api:0.11.5")
            dependency("io.jsonwebtoken:jjwt-impl:0.11.5")
            dependency("io.jsonwebtoken:jjwt-jackson:0.11.5")
            dependency("commons-codec:commons-codec:1.16.0")
            dependency("org.liquibase:liquibase-core:4.24.0")
            dependency("org.postgresql:postgresql:42.7.3")
        }
    }

    dependencies {
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

// Common module
project(":common") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.mapstruct:mapstruct")

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
            if (module != Module.GATEWAY) {
                implementation("org.springframework.boot:spring-boot-starter-web")
                implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
            }

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