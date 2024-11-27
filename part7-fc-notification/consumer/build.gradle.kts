plugins {
    id("project-conventions")
    id("java-conventions")
    id("spring-conventions")
}

dependencies {
    implementation(project(":part7-fc-notification:core"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")

    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}