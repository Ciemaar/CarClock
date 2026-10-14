# Unit 2: Update Theme.kt with Sky Blue Color Constant

## Objective
Add the sky blue color constant to the project's theme system for use throughout the application. This constant will be used by the time-based theming system and should be accessible to all UI components.

## Context
- **Issue**: #4 - Make the background for the clock sky blue during the day and black at night
- **Plan**: See `plans/04-time-based-background-colors.md` for full implementation plan
- **Part of**: Issue #4 Implementation (Unit 2 of 5)
- **Dependency**: Unit 1 (TimeBasedThemeUtil) should be completed first

## Requirements

### File to Modify
`app/src/main/java/com/infiniti/clock/ui/theme/Theme.kt`

### Acceptance Criteria
- [ ] `DaySkyBlue` color constant added (value: `Color(0xFF5B9BD5)`)
- [ ] Color is declared at the module level (accessible from other files)
- [ ] KDoc comment explains purpose: "Automotive-friendly sky blue for daytime clock background"
- [ ] KDoc mentions why this specific shade was chosen (readability, reduced glare)
- [ ] No changes to existing Material3 theme logic
- [ ] No changes to existing dark/light color schemes
- [ ] All existing theme tests still pass
- [ ] Code passes spotlessCheck, detekt, and dokkaHtml

### Implementation Details

Add the color constant after the existing color definitions in `Theme.kt`. Based on the current file structure, it should be added near other color definitions like `InfinitiGreen` and `InfinitiGold`.

**Location**: After line with `InfinitiGold` definition

**Code to add**:
```kotlin
/**
 * Automotive-friendly sky blue for daytime clock background.
 * 
 * This shade (RGB 91, 189, 213) is chosen for:
 * - Readability on automotive dashboards with varying brightness
 * - Reduced glare during daytime use
 * - Sufficient contrast with black clock hands for Driver Distraction Guidelines compliance
 * - Aesthetic continuity with the Infiniti brand color palette
 */
val DaySkyBlue = Color(0xFF5B9BD5)
```

### Verification
- [ ] Color is accessible when importing from `Theme.kt`
- [ ] Color value displays correctly as sky blue when tested visually
- [ ] No IDE warnings about unused constants
- [ ] All tests pass:
  ```bash
  ./gradlew spotlessCheck
  ./gradlew detekt
  ./gradlew dokkaHtml
  ./gradlew testDebugUnitTest
  ```

## Notes
- This is a simple constant addition with no functional changes
- The hex value `0xFF5B9BD5` corresponds to RGB(91, 189, 213)
- Do not modify existing Material3 theme colors or logic
- This constant will be imported and used by `TimeBasedThemeUtil` in Unit 1
