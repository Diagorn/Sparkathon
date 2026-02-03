pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("org.springframework.boot") version "3.2.4"
        id("io.spring.dependency-management") version "1.1.4"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "sparkathon"

Module.ALL_MODULES.forEach { module ->
    include(module.moduleName)
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