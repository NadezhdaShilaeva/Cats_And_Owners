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
    implementation("org.projectlombok:lombok:1.18.22")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("jakarta.persistence:jakarta.persistence-api:3.0.0")
    implementation("org.hibernate.orm:hibernate-core:6.1.7.Final")
}