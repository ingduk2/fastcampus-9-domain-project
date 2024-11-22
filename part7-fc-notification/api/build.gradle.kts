plugins {
    id("project-conventions")
    id("java-conventions")
    id("spring-conventions")
}

dependencies {
    implementation(project(":part7-fc-notification:core"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
}