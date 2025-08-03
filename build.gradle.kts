plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.danilopianini.gradle-java-qa") version "1.96.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories { // Where to search for dependencies
    mavenCentral()
}

dependencies {
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")

    // Maven dependencies are composed by a group name, a name and a version, separated by colons
    
    // Currently not usefull
    // implementation("com.google.guava:guava:33.4.0-jre")
    // implementation("org.apache.commons:commons-collections4:4.4")
    // implementation("org.apache.commons:commons-lang3:3.17.0")

    /*
     * Simple Logging Facade for Java (SLF4J) with Apache Log4j
     * See: http://www.slf4j.org/
     */

    // ONLY FOR STRUCTURED LOGGING: may be usefull in the future
    //val slf4jVersion = "2.0.16"
    //implementation("org.slf4j:slf4j-api:$slf4jVersion")
    //Logback backend for SLF4J
    //runtimeOnly("ch.qos.logback:logback-classic:1.5.12")

    // JUnit API and testing engine
    val jUnitVersion = "5.11.3"
    // when dependencies share the same version, grouping in a val helps to keep them in sync
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")

}

/* application {
    // Define the main class for the application.
} */

tasks.withType<Checkstyle> {
    exclude("api/others/**")
    exclude("impl/others/**")
}

tasks.withType<Pmd> {
    exclude("api/others/**")
    exclude("impl/others/**")
}


tasks.test {
    useJUnitPlatform()
}