//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "me.dm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}



//tasks.withType<KotlinCompile> {
//    kotlinOptions.jvmTarget = "1.8"
//}

application {
    mainClass.set("MainKt")
}

dependencies{
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
}