---
conventions-version: 1
conventions-required: 1
---

# Local Conventions

This file states the values this repository's workflow currently repeats across its templates and commands: the check commands, the dispatch invocation, the sensitive-path list, and how repository identity reaches a prompt.

## 1. The check commands

What this repository runs to verify a unit.

- build: `./gradlew assembleDebug` — Build the Android project and verify no compilation errors
- test: `./gradlew testDebugUnitTest` — Run unit tests using Robolectric for local Compose UI testing
- coverage: `./gradlew koverVerifyDebug` — Verify test coverage meets minimum 75% line coverage threshold
- format: `./gradlew spotlessCheck` — Verify Kotlin and Markdown formatting (combines ktlint and Prettier)
- format-apply: `./gradlew spotlessApply` — Apply automatic formatting fixes
- lint: `./gradlew lintDebug` — Android code quality and lint checks
- static-analysis: `./gradlew detekt` — Kotlin static analysis with Compose-specific rules
- docs: `./gradlew dokkaHtml` — Verify KDoc documentation presence (fails if public elements undocumented)

*(Source: `.github/workflows/pr-check.yml`, `.githooks/pre-commit`, and `AGENTS.md` Section 4)*

## 2. The dispatch invocation

How a unit prompt reaches an implementing agent.

None

*(This repository uses direct Git workflow without automated dispatch tooling.)*

## 3. The sensitive-path list

The paths whose modification escalates to a human regardless of test results.

- `AGENTS.md` — Agent instructions, constraints, and Android Automotive requirements
- `.github/workflows/pr-check.yml` — CI configuration and check command automation
- `.githooks/pre-commit` — Pre-commit hook that enforces local checks
- `build.gradle.kts` — Root Gradle build configuration and plugin versions
- `app/build.gradle.kts` — Android app build configuration and dependencies
- `settings.gradle.kts` — Gradle project structure and module configuration
- `gradle.properties` — Gradle system properties and build behavior
- `gradle/wrapper/gradle-wrapper.properties` — Gradle version pinning
- `local.properties` — Android SDK path and local environment configuration
- `AndroidManifest.xml` — Android Automotive declarations (critical: automotive feature and distraction optimization flags)
- `.gitignore` — Git tracking rules and excluded paths
- `DEVELOPER_GUIDE.md` — Developer documentation and onboarding
- `USER_GUIDE.md` — User-facing documentation and feature descriptions
- `config/detekt/detekt.yml` — Static analysis rules for Kotlin and Compose

## 4. How repository identity reaches a prompt

The repository uses hardcoded identifiers and configuration files rather than dynamic injection:

- **Package name**: `com.infiniti.clock` is hardcoded in Kotlin source code and AndroidManifest.xml
- **Application branding**: "Infiniti Clock" app name is embedded in manifest and UI resources
- **Color scheme**: Defined in `app/src/main/java/com/infiniti/clock/ui/theme/Theme.kt` with specific Infiniti brand colors (Black, White, Dark Green/Gold accents)
- **Platform constraints**: API level 34+, Android Automotive OS specific features hardcoded in manifest

No environment variables or template parameters are used for repository identity injection.

## 5. Templates to copy

Propose copying the workflow templates to this repository:

- `templates/plans/TEMPLATE.md`    -> `plans/TEMPLATE.md`
- `templates/prompts/TEMPLATE.md`  -> `prompts/TEMPLATE.md`
- `templates/reports/TEMPLATE.md`  -> `reports/TEMPLATE.md`
- `templates/plans/README.md`      -> `plans/README.md`
- `templates/prompts/README.md`    -> `prompts/README.md`
- `templates/reports/README.md`    -> `reports/README.md`

## 6. Runtime files ignore

Propose adding the following files to `.gitignore` to prevent committing workflow runtime state:

- `.dispatch-state.json`
- `.claude/.watch-heartbeat`
- `.claude/.watch-status.json`
- `.claude/.open-pr-cache`
- `.claude/.watch-polls-cache`
