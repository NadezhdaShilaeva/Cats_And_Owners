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
    implementation(project(":HibernateService:dao"))
    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}