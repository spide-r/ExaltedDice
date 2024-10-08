plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.3"
}

group = "me.spider"
version = "1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("net.dv8tion:JDA:5.1.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Title" to project.name, "Implementation-Version" to project.version, "Main-Class" to "me.spider.Main"))
    }
}

tasks.shadowJar {
    archiveBaseName.set("ExaltedDice")
    archiveClassifier.set("")
    archiveVersion.set("")
}




