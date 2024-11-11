pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "fastcampus-9-domain-project"

include(
    "part1-community-feed:ch2-oop"
)