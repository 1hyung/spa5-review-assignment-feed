plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.teamsparta"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") //@Entity 사용 가능
    implementation("org.springframework.boot:spring-boot-starter-validation") //@NotBlank 사용 가능
    implementation("org.springframework.boot:spring-boot-starter-security") //passwordEncoder 사용 가능
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2") // JWT 사용 가능
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2") // JWT 사용 가능
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2") // JWT 사용 가능
    runtimeOnly("com.h2database:h2") // H2 사용 가능
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.13") // Swagger UI
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.13") // Swagger UI
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
