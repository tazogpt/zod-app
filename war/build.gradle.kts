plugins {
    kotlin("plugin.spring")
    war
}

kotlin {
    jvmToolchain(25)
}

springBoot {
    mainClass.set("zod.WebApplication")
}

tasks.bootJar {
    enabled = false
}

tasks.bootWar {
    enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation(project(":app"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
