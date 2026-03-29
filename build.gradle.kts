plugins {
    base
    jacoco
}

repositories {
    mavenCentral()
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
