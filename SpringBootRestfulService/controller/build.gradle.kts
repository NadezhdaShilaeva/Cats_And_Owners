plugins {
    id("java")
}

group = "com.shilaeva"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":SpringBootRestfulService:service"))
    implementation("org.springframework.boot:spring-boot-devtools:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.5")
}