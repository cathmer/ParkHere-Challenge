import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath("org.testcontainers:postgresql:1.20.3")
        classpath("org.postgresql:postgresql:42.7.4")
        classpath("org.flywaydb:flyway-core:10.21.0")
        classpath("org.flywaydb:flyway-database-postgresql:10.21.0")
    }
}

plugins {
    `java-library`
    groovy
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("nu.studer.jooq") version "9.0"
    id("org.flywaydb.flyway") version "10.21.0"
    id("com.diffplug.spotless") version "6.25.0"
}

springBoot {
    extra["jooq.version"] = jooq.version.get()
}

group = "com.parkhere"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.flywaydb:flyway-core:10.21.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.21.0")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.21.0")
    jooqGenerator("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.20.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val containerInstance: PostgreSQLContainer<Nothing>? =
    if ("generateJooq" !in project.gradle.startParameter.excludedTaskNames) {
        PostgreSQLContainer<Nothing>(
            DockerImageName.parse(
                "postgres:17.1-alpine",
            ),
        ).apply {
            println("Starting container")
            withDatabaseName("generate-db")
            start()
        }
    } else {
        null
    }

flyway {
    url = containerInstance?.jdbcUrl
    user = containerInstance?.username
    password = containerInstance?.password
}

jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = containerInstance?.jdbcUrl
                    user = containerInstance?.username
                    password = containerInstance?.password
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        excludes = "flyway.*"
                    }
                    target.apply {
                        packageName = "com.parkhere.reservation_service.jooq.generated"
                    }
                }
            }
        }
    }
}

tasks.named("generateJooq") {
    dependsOn(tasks.named("flywayMigrate"))
    doLast {
        containerInstance?.stop()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    java {
        target("**/*.java")
        targetExclude("build/generated-src/**/*.*")
        importOrder()
        removeUnusedImports()
        googleJavaFormat()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

tasks.bootJar {
    archiveBaseName = "boot"
    archiveClassifier = "app"
}
