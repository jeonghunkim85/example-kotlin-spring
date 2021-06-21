import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	// 기본적으로 추가되는
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"

	kotlin("kapt") version "1.4.32" // kotlin annotation plugin
	kotlin("plugin.allopen") version "1.4.32" //
	kotlin("plugin.noarg") version "1.4.32"
}

group = "com.deliwind"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

// hibernate 가 entity class 에 인수가 없는 생성자를 요구하는데, 이를 자동으로 생성해주는 plugin
noArg {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

allOpen {
	// 특정 annotation 이 붙은 class 에 한하여 open class 로 만들어줍니다. (ORM 지연로딩)
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

object Versions {
	const val kotlinVersion = "1.4"
	const val mapstructVersion = "1.4.2.Final"
	const val swaggerVersion = "3.0.0"
}

object TestVersions {
	const val kotestVersion = "4.4.3"
	const val mockkVersion = "1.11.0"
	const val springmockkVersion = "3.0.1"
}

dependencies {

	// kotlin 관련
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// spring-boot-configuration-processor
	kapt("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.boot:spring-boot-configuration-processor")

	// h2
	runtimeOnly("com.h2database:h2:1.4.200")

	// mapstruct
	implementation("org.mapstruct:mapstruct:${Versions.mapstructVersion}")
	kapt("org.mapstruct:mapstruct-processor:${Versions.mapstructVersion}")

	// swagger 3.0
	implementation("io.springfox:springfox-boot-starter:${Versions.swaggerVersion}")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testRuntimeOnly("com.h2database:h2:1.4.200")

	// kotest
	testImplementation("io.kotest:kotest-runner-junit5:${TestVersions.kotestVersion}")
	testImplementation("io.kotest:kotest-assertions-core:${TestVersions.kotestVersion}")
	testImplementation("io.kotest:kotest-extensions-spring:${TestVersions.kotestVersion}")
	testImplementation("io.mockk:mockk:${TestVersions.mockkVersion}")
	testImplementation("com.ninja-squad:springmockk:${TestVersions.springmockkVersion}")
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
			languageVersion = Versions.kotlinVersion
			apiVersion = Versions.kotlinVersion
		}
	}

	withType<Test> {
		useJUnitPlatform()
	}

	bootJar {
		archiveFileName.set(archiveBaseName.get() + "." + archiveExtension.get())
	}

	clean {
		delete = setOf("build", "out")
	}
}
