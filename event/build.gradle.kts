// event/build.gradle.kts
plugins {
    id("org.springframework.boot")
    id("org.flywaydb.flyway") version "9.22.3"
}

flyway {
    // Flyway config
    url = "jdbc:postgresql://localhost:5432/event?currentSchema=event"
    user = "postgres"
    password = "postgres"
    locations = arrayOf("classpath:db/migration")
}

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.kafka:spring-kafka")

    // Database & Migration
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")

    // Utilities
    implementation("commons-codec:commons-codec")

    // MapStruct аннотационные процессоры
    annotationProcessor("org.mapstruct:mapstruct-processor")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding")

    // Kafka Testing
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks {
    bootJar {
        archiveFileName.set("event.jar")
    }
}