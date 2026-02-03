dependencies {
    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // gRPC
    implementation("net.devh:grpc-spring-boot-starter:2.15.0.RELEASE")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api")
    runtimeOnly("io.jsonwebtoken:jjwt-impl")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson")

    // Mac OS specific dependency
    val isMacOS = System.getProperty("os.name").lowercase().contains("mac")
    val isArm64 = System.getProperty("os.arch").lowercase().contains("aarch64")

    if (isMacOS && isArm64) {
        runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.72.Final") {
            artifact {
                classifier = "osx-aarch_64"
            }
        }
    }
}

tasks {
    bootJar {
        archiveFileName.set("gw.jar")
    }
}