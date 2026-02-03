dependencies {
    // Utilities
    annotationProcessor("org.mapstruct:mapstruct-processor")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding")
}

tasks {
    bootJar {
        archiveFileName.set("lineboard.jar")
    }
}