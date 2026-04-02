plugins {
    id("buildlogic.java-conventions")
    id("org.springframework.boot")
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":catalog"))
    implementation(project(":common"))
    implementation(project(":shared"))
    implementation(project(":cart"))
    implementation(project(":order"))
    implementation(project(":inventory"))
    implementation(project(":payment"))
    implementation(project(":search"))
    implementation(project(":notification"))
}

tasks.register<JacocoReport>("testCodeCoverageReport") {
    val testTasks = subprojects.flatMap { it.tasks.withType<Test>() }
    dependsOn(testTasks)

    executionData.setFrom(testTasks.map { it.extensions.getByType<JacocoTaskExtension>().destinationFile })
    sourceDirectories.setFrom(subprojects.flatMap { it.the<SourceSetContainer>()["main"].allSource.srcDirs })
    classDirectories.setFrom(subprojects.flatMap { it.the<SourceSetContainer>()["main"].output })

    reports {
        html.required.set(true)
    }
}
