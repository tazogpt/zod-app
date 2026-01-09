plugins {
    kotlin("plugin.jpa")
    kotlin("kapt")
}

kotlin {
    jvmToolchain(25)
}

allOpen {
    annotation("zod.common.infra.annotation.AllOpen")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("zod.common.infra.annotation.NoArgs")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")

    implementation("io.github.openfeign.querydsl:querydsl-jpa:7.1")
    kapt("io.github.openfeign.querydsl:querydsl-apt:7.1:jakarta")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

sourceSets {
    main {
        java.srcDir("build/generated/source/kapt/main")
    }
}

springBoot {
    mainClass.set("zod.Application")
}