# AI Agent Development Guidelines

This file contains instructions and tips for AI agents working on the Infiniti Clock app repository. When modifying this codebase, you MUST adhere to the following rules:

## Agent Workflow

- Always start tasks with a deep planning mode. Ask clarifying questions using `request_user_input` and `message_user` until requirements are completely understood. Create a plan using `set_plan` and wait for approval. Once approved, execute the plan autonomously without asking for further confirmation.

## 1. Environment & Build Requirements

- The project requires Java 17 to compile.
- The Android SDK is located at `/opt/android-sdk` in the development environment.
- The project uses a pure-JVM build state. Formatting is managed by Spotless (`com.diffplug.spotless`) combining ktlint and Prettier, avoiding Node.js dependencies. Detekt (`io.gitlab.arturbosch.detekt`) is used for static analysis, with Compose-specific rule exceptions configured in `config/detekt/detekt.yml`.

## 2. Android Automotive Constraints

This is a native Android Automotive OS app (API 34+), not a standard mobile app.

- **NEVER** remove `<uses-feature android:name="android.hardware.type.automotive" android:required="true" />` from the `AndroidManifest.xml`.
- **NEVER** remove `<meta-data android:name="distractionOptimized" android:value="true" />` from the `AndroidManifest.xml`. This tag is critical; without it, the car's OS will block the clock while driving.
- When adding new UI elements or activities, consider Driver Distraction Guidelines (DDG). Do not add features that require scrolling long text, complex animations, or intricate interactions. Keep touch targets large and easily glanceable.

## 3. UI and Theming (Jetpack Compose)

- All UI MUST be written using **Jetpack Compose**. Do not use traditional XML layouts (except for standard resources like strings, themes, and launcher icons).
- **Colors**: Adhere to the Infiniti color scheme established in `app/src/main/java/com/infiniti/clock/ui/theme/Theme.kt` (Black, White, Dark Green/Gold accents).
- **Night Mode**: The app must respect the system dark mode (`isSystemInDarkTheme()`). In dark mode, the UI must use a simplified, pure black/white palette to prevent dashboard glare at night.
- **Canvas Drawing**: When drawing circular patterns in Jetpack Compose `Canvas` (like clock ticks or hands), prefer using `DrawScope.rotate(degrees)` over manual trigonometry (`sin`/`cos`) for better simplicity and readability.

## 4. Testing and Verification

- **Test-Driven Development**: When adding new logic (like date formatting or new settings), write a unit test in `app/src/test` first.
- **UI Tests**: The project uses Robolectric (`org.robolectric:robolectric`) for local Compose UI testing to allow test execution without a physical device or emulator.
- **Infinite Loops in Tests**: When writing Compose UI tests for components with infinite loops (e.g., a ticking clock using `LaunchedEffect`), pause the main clock (`composeTestRule.mainClock.autoAdvance = false`) and advance it manually to prevent test timeouts and hangs.
- **Test Coverage**: The project uses Kover (`org.jetbrains.kotlinx.kover`) for test coverage.
- **Run Tests**: After making changes, you MUST verify your work by running:
  - `./gradlew assembleDebug` (Build the Android project)
  - `./gradlew testDebugUnitTest` (Run unit tests)
  - `./gradlew koverVerifyDebug` (Verify test coverage, minimum 75% line coverage)
  - `./gradlew spotlessApply` (Format Kotlin and Markdown)
  - `./gradlew spotlessCheck` (Verify formatting)
  - `./gradlew detekt` (Run static analysis)
  - `./gradlew dokkaHtml` (Verify KDoc documentation)

## 5. Build Artifacts

- **Edit Source, Not Artifacts**: Do not directly edit files in `app/build/` or `.gradle/`. Always modify the source Kotlin files (`.kt`), Gradle scripts (`.kts`), or resource files (`.xml` in `src/main/res/`), then rebuild using `./gradlew`.

## 6. Documentation

- **KDoc**: Always document each Kotlin function, class, and composable using meaningful, context-aware KDoc (avoid placeholders). The project uses the Dokka plugin (`org.jetbrains.dokka`) configured to fail the build if public elements are undocumented.
- **Markdown Bullet Points**: For Markdown documentation, consistently use hyphens (`-`) instead of asterisks (`*`) for unordered bullet points.
- Project documentation includes `USER_GUIDE.md` for end-user instructions, `DEVELOPER_GUIDE.md` for technical details, `LEARNING_GUIDE.md` for Android/Kotlin learners, `EVALUATION.md` for tooling architecture decisions, and `SESSION_INSTRUCTIONS.md` for a chronological record of instructions, with `README.md` serving as a high-level overview linking to all guides. Ensure documentation is updated if the feature or UI flow changes significantly.

## 7. Git Workflow Rules

- If you add a new feature or change the UI flow, you must update the `USER_GUIDE.md` and/or `DEVELOPER_GUIDE.md` accordingly. Ensure the synthetic screenshots in the `README.md` are updated if the layout changes significantly.

## 6. Branch Management and Merging

- When working on an existing, previous branch (e.g., rebasing or merging features), features MUST NOT be removed if they have been added to the main branch in the intermediate interval.
- All branches being merged in, as well as their matching PRs, MUST be referenced in the commit comments and any new PRs.
- When rebasing or merging existing branches, features added to the main branch in the intermediate interval must not be removed. All merged branches and their matching PRs must be referenced in the commit comments and any new PRs.
- GitHub Actions CI is configured in `.github/workflows/pr-check.yml` (with `permissions: read-all`) to automatically enforce code quality (`./gradlew lintDebug`), Kotlin and Markdown formatting (`./gradlew spotlessCheck`), static analysis (`./gradlew detekt`), KDoc verification (`./gradlew dokkaHtml`), unit testing (`./gradlew testDebugUnitTest`), test coverage minimums (`./gradlew koverVerifyDebug`), and build verification (`./gradlew assembleDebug`) on pull requests and pushes to the main branch.
- A local pre-commit hook in `.githooks/pre-commit` automatically runs `spotlessCheck`, `detekt`, and `dokkaHtml`.
