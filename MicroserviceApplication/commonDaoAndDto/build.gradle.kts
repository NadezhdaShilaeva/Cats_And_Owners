plugins {
    id("java")
    id("io.freefair.lombok") version "6.6.2"
}

group = "com.shilaeva"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.5")
    implementation("org.springframework.boot:spring-boot-devtools:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.5")
    implementation("org.postgresql:postgresql:42.5.4")
}