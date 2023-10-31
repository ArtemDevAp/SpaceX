@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization)
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.agp)
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.navigation.safe.args.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    detekt {
        toolVersion = "1.21.0"
        config.from(rootProject.files("config/detekt/detekt.yml"))
    }
}

typealias DetektCreateBaselineTask = io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
val detektProjectBaseline by tasks.registering(DetektCreateBaselineTask::class) {
    description = "Ovveride current baseline."
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)
    setSource(files(rootDir))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "1.8"
}

tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    android.set(true)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.JSON)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}