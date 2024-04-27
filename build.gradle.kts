import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.google.protobuf") version "0.9.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

val protocolBuffersVersion = "4.26.1"
val grpcVersion = "1.63.0"

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:${protocolBuffersVersion}"

	}
	plugins {
		register("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
		}
		/* Disabled: Generates Kotlin coroutine native services
        register("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${grpcKotlinVersion}:jdk8@jar"
        }
         */
	}
	generateProtoTasks {
		all().forEach {
			it.builtins {
				register("kotlin")
			}
			it.plugins {
				register("grpc")
				//register("grpckt")
			}
		}
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")


	// Protocol Buffers and gRPC
	implementation("io.grpc:grpc-stub:${grpcVersion}")
	implementation("io.grpc:grpc-protobuf:${grpcVersion}")
	implementation("com.google.protobuf:protobuf-kotlin:${protocolBuffersVersion}")
	implementation("com.google.protobuf:protobuf-java-util:${protocolBuffersVersion}")
	runtimeOnly("io.grpc:grpc-netty-shaded:${grpcVersion}")




	implementation("io.r2dbc:r2dbc-postgresql:0.8.12.RELEASE")
	implementation("org.postgresql:postgresql:42.2.14")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
