plugins {
    id("project-conventions")
    id("java-conventions")
    id("spring-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.testcontainers:testcontainers:1.20.4")

    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}