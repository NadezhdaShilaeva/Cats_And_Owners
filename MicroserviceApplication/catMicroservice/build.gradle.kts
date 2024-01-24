plugins {
    id("java")
}

group = "com.shilaeva"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":MicroserviceApplication:commonDaoAndDto"))
    implementation("org.springframework.boot:spring-boot-devtools:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.5")
    implementation("org.springframework.kafka:spring-kafka:3.0.7")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}