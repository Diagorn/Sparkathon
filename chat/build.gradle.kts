plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    java
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf(
            "-parameters",
            "-Amapstruct.defaultComponentModel=spring",
            "-Amapstruct.suppressGeneratorTimestamp=true"
        ))
    }

    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
    }

    bootJar {
        archiveFileName.set("chat.jar")
        manifest {
            attributes(
                "Implementation-Title" to "chat",
                "Implementation-Version" to project.version
            )
        }
    }

    bootRun {
        systemProperties(System.getProperties().mapKeys { it.key.toString() })
    }
}

springBoot {
    mainClass.set("com.diagorn.chat.ChatApplication")
    buildInfo()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    // Spring Boot Starters (основные)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // WebSocket поддержка
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // MapStruct аннотационные процессоры (нужны, если используете маппинги)
    annotationProcessor("org.mapstruct:mapstruct-processor")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Внутренние зависимости
    implementation(project(":common"))

    // Тестирование
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}