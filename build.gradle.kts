plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.3"
}

group = "me.spider"
version = "1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://m2.chew.pro/releases") }

}

dependencies {
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:3.46.1.3")
    implementation("net.dv8tion:JDA:5.1.2")
    implementation("pw.chew:jda-chewtils:2.0")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
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




