plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.dokka") version "1.9.20" apply false
    id("org.jetbrains.kotlinx.kover") version "0.8.3" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.6" apply false
    id("com.diffplug.spotless") version "6.25.0"
}

allprojects {
    apply(plugin = "com.diffplug.spotless")

    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint().editorConfigOverride(mapOf("ktlint_function_naming_ignore_when_annotated_with" to "Composable"))
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
        format("markdown") {
            target("**/*.md")
            targetExclude("**/build/**/*.md")
            prettier()
        }
    }
}
