# Tooling Evaluation & Architecture Decisions

This document outlines the evaluation of various tools for formatting, linting, testing, and documentation in the Infiniti Clock Android Automotive repository, and details the final adopted stack.

## 1. Formatting & Code Style

**Evaluated:** `org.jlleitschuh.gradle.ktlint` vs. `Spotless` vs. native `npm Prettier`

- **`org.jlleitschuh.gradle.ktlint`**: Great dedicated plugin for Kotlin, but lacks multi-language support (like Markdown).
- **`npm Prettier`**: Industry standard for Markdown and JSON, but introduces a heavy `node_modules` dependency into an otherwise pure Gradle/Kotlin environment, complicating CI setup.
- **`Spotless` (Adopted)**: Spotless is a unified Gradle wrapper that can natively run `ktlint` (for Kotlin files) and `Prettier` (for Markdown/JSON files) seamlessly within the JVM without requiring users to install Node.js locally. It consolidates formatting into a single `./gradlew spotlessCheck` command.

## 2. Static Analysis (Linting)

**Evaluated:** `Android Lint` vs. `Detekt`

- **`Android Lint` (Adopted)**: Maintained by Google, it is highly specialized for Android contexts. It detects missing accessibility tags, incorrect metadata (crucial for our `distractionOptimized` checks), and invalid resource references.
- **`Detekt` (Adopted)**: Detekt is the premier static code analysis tool specifically built for Kotlin. It analyzes code smells, complexity, and idiomatic Kotlin practices that Android Lint misses.
- **Decision**: We adopt _both_. Detekt analyzes Kotlin smells, and Android Lint analyzes Android-specific bugs.

## 3. Test Coverage

**Evaluated:** `Jacoco` vs. `Kover`

- **`Jacoco`**: The legacy standard for Java coverage. However, it notoriously struggles with Kotlin inline functions and Jetpack Compose coroutine artifacts, often reporting artificially low coverage.
- **`Kover` (Adopted)**: Developed by JetBrains specifically for Kotlin. It perfectly understands Kotlin language features, `LaunchedEffect` coroutines, and Compose structures out-of-the-box. It also enforces coverage bounds (`minBound`) cleanly within the `build.gradle.kts`.

## 4. Documentation Engine

**Evaluated:** `JavaDoc` vs. `Dokka`

- **`Dokka` (Adopted)**: Dokka is the undisputed official documentation engine for Kotlin. It natively understands KDoc syntax and Kotlin concepts (like data classes and extension functions). We utilize it strictly with `failOnWarning = true` to force documentation on all public functions in PRs.

## 5. UI Testing Framework

**Evaluated:** `Espresso` vs. `Compose UI Test` vs. `Robolectric`

- **`Compose UI Test` + `Robolectric` (Adopted)**: `Espresso` is outdated for Jetpack Compose. By combining `composeTestRule` with `Robolectric`, we can run the entire UI test suite natively on the JVM (local machine or CI) without needing to boot a heavy, slow Android Automotive Emulator.

---

### The Final Adopted Stack

To provide a pristine developer experience, the project strictly uses the following Gradle-driven stack:

1.  **Spotless** (`./gradlew spotlessCheck`) - Unified Kotlin (`ktlint`) and Markdown (`prettier`) formatting.
2.  **Detekt** (`./gradlew detekt`) - Kotlin code smell analysis.
3.  **Android Lint** (`./gradlew lintDebug`) - Android-specific bug detection.
4.  **Kover** (`./gradlew koverVerifyDebug`) - 75% strict test coverage verification.
5.  **Dokka** (`./gradlew dokkaHtml`) - KDoc presence enforcement.
