# AI Agent Development Guidelines

This file contains instructions and tips for AI agents working on the Infiniti Clock app repository. When modifying this codebase, you MUST adhere to the following rules:

## 1. Android Automotive Constraints

This is a native Android Automotive OS app (API 34+), not a standard mobile app.

- **NEVER** remove `<uses-feature android:name="android.hardware.type.automotive" android:required="true" />` from the `AndroidManifest.xml`.
- **NEVER** remove `<meta-data android:name="distractionOptimized" android:value="true" />` from the `AndroidManifest.xml`. This tag is critical; without it, the car's OS will block the clock while driving.
- When adding new UI elements or activities, consider Driver Distraction Guidelines (DDG). Do not add features that require scrolling long text, complex animations, or intricate interactions. Keep touch targets large and easily glanceable.

## 2. UI and Theming (Jetpack Compose)

- All UI MUST be written using **Jetpack Compose**. Do not use traditional XML layouts (except for standard resources like strings, themes, and launcher icons).
- **Colors**: Adhere to the Infiniti color scheme established in `app/src/main/java/com/infiniti/clock/ui/theme/Theme.kt` (Black, White, Dark Green/Gold accents).
- **Night Mode**: The app must respect the system dark mode (`isSystemInDarkTheme()`). In dark mode, the UI must use a simplified, pure black/white palette to prevent dashboard glare at night.

## 3. Testing and Verification

- **Test-Driven Development**: When adding new logic (like date formatting or new settings), write a unit test in `app/src/test` first.
- **UI Tests**: When modifying the Jetpack Compose UI, update or add tests in `app/src/androidTest`.
- **Run Tests**: After making changes, you MUST verify your work by running:
  - `./gradlew lintDebug` (to enforce code quality)
  - `./gradlew testDebugUnitTest` (to run logic tests)
- **Compilation**: Ensure the app compiles successfully by running `./gradlew assembleDebug`.

## 4. Build Artifacts

- **Edit Source, Not Artifacts**: Do not directly edit files in `app/build/` or `.gradle/`. Always modify the source Kotlin files (`.kt`), Gradle scripts (`.kts`), or resource files (`.xml` in `src/main/res/`), then rebuild using `./gradlew`.

## 5. Documentation

- If you add a new feature or change the UI flow, you must update the `USER_GUIDE.md` and/or `DEVELOPER_GUIDE.md` accordingly. Ensure the synthetic screenshots in the `README.md` are updated if the layout changes significantly.
