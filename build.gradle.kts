plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0" apply false
    id("org.jetbrains.dokka") version "1.9.20" apply false
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
