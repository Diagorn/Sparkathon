// lineboard/build.gradle.kts (минимальный)
dependencies {
    // MapStruct аннотационные процессоры
    annotationProcessor("org.mapstruct:mapstruct-processor")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding")
}

tasks {
    bootJar {
        archiveFileName.set("lineboard.jar")
    }
}