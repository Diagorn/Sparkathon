dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
    }

    // JWT dependencies
    implementation("io.jsonwebtoken:jjwt-api")
    runtimeOnly("io.jsonwebtoken:jjwt-impl")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    bootJar {
        archiveFileName.set("gw.jar")
    }
}