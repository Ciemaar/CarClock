# Unit 4: Update ClockApp Composable Background and DigitalClock Colors

## Objective
Modify the main ClockApp composable to use time-based colors for the background instead of system dark mode. Also update the DigitalClock text colors to match the time-based scheme.

## Context
- **Issue**: #4 - Make the background for the clock sky blue during the day and black at night
- **Plan**: See `plans/04-time-based-background-colors.md` for full implementation plan
- **Part of**: Issue #4 Implementation (Unit 4 of 5)
- **Dependencies**: Units 1-2 should be completed first

## Requirements

### File to Modify
`app/src/main/java/com/infiniti/clock/MainActivity.kt` - the `ClockApp()` composable

### Acceptance Criteria
- [ ] `ClockApp()` background changes based on time, not system theme
- [ ] Background is sky blue (`Color(0xFF5B9BD5)`) from 7:45 AM - 5:00 PM
- [ ] Background is black (`Color.Black`) from 5:00 PM - 7:45 AM
- [ ] `DigitalClock()` text color is black during day, white at night
- [ ] Time-based colors are derived from `TimeBasedThemeUtil`
- [ ] No `isSystemDarkTheme()` calls remain in ClockApp
- [ ] Settings overlay remains visible and functional over time-based background
- [ ] Settings button is clickable regardless of background color
- [ ] All MainActivityTest tests pass
- [ ] Manual testing confirms background color transitions at 7:45 AM and 5:00 PM
- [ ] Code passes spotlessCheck, detekt, and dokkaHtml

### Implementation Pattern

**Current approach (to be replaced)**:
```kotlin
val backgroundColor = if (isSystemDarkTheme()) Color.Black else Color(0xFFF5F5F5)
```

**New approach (time-based)**:
```kotlin
var currentTime by remember { mutableStateOf(Calendar.getInstance()) }

LaunchedEffect(Unit) {
    while (true) {
        currentTime = Calendar.getInstance()
        delay(1000) // Update every second
    }
}

val isDayTime = TimeBasedThemeUtil.isDayTime(currentTime)
val backgroundColor = if (isDayTime) DaySkyBlue else Color.Black
val textColor = TimeBasedThemeUtil.getClockColor(isDayTime)
```

### Full Composable Structure

The `ClockApp()` should maintain its current structure but update color logic:

```kotlin
@Composable
fun ClockApp() {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }
    var showSettings by remember { mutableStateOf(false) }
    var showDate by remember { mutableStateOf(false) }

    // Update time every second
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance()
            delay(1000)
        }
    }

    val isDayTime = TimeBasedThemeUtil.isDayTime(currentTime)
    val backgroundColor = if (isDayTime) DaySkyBlue else Color.Black
    val textColor = TimeBasedThemeUtil.getClockColor(isDayTime)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        // Clock display logic (unchanged)
        
        // Settings button (unchanged)
        
        // Settings overlay (if shown)
    }
}
```

### Imports to Add
```kotlin
import com.infiniti.clock.util.TimeBasedThemeUtil
import com.infiniti.clock.ui.theme.DaySkyBlue
import java.util.Calendar
```

### Digital Clock Updates
If `DigitalClock()` is called within `ClockApp()`, ensure its text color uses the time-based `textColor`:

```kotlin
DigitalClock(
    modifier = Modifier...,
    textColor = textColor, // Pass time-based color instead of theme color
    showDate = showDate
)
```

### Settings Overlay Accessibility
- Verify settings button remains clickable and visible over both sky blue and black backgrounds
- Ensure settings overlay content is readable on both backgrounds
- Test contrast ratio meets WCAG AA standards for accessibility

### Testing Approach
- Update `MainActivityTest.kt` to include time-based background color tests
- Test background color at 9 AM (should be sky blue)
- Test background color at 9 PM (should be black)
- Test settings overlay is still interactive on both backgrounds
- Use `composeTestRule.mainClock.advanceTimeBy()` to simulate time passage
- Test exact boundary times (7:45:00 AM, 5:00:00 PM)

### Example Test Cases
```kotlin
@Test
fun testClockAppDayBackground() {
    val dayTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 10)
        set(Calendar.MINUTE, 0)
    }
    // Verify background is sky blue
}

@Test
fun testClockAppNightBackground() {
    val nightTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 22)
        set(Calendar.MINUTE, 0)
    }
    // Verify background is black
}

@Test
fun testSettingsAccessibleOnBothBackgrounds() {
    // Verify settings button works on day background
    // Verify settings button works on night background
}
```

### Verification
Run the following commands:
```bash
./gradlew spotlessCheck
./gradlew detekt
./gradlew dokkaHtml
./gradlew lintDebug         # Android lint checks
./gradlew testDebugUnitTest
./gradlew koverVerifyDebug  # Verify 75%+ coverage maintained
```

### Manual Testing
1. Run the app in emulator
2. Note the background at 10:00 AM (should be sky blue)
3. Note the background at 10:00 PM (should be black)
4. Test settings overlay is accessible on both backgrounds
5. Verify settings can be toggled/changed on both backgrounds

## Notes
- The continuous time update ensures the background transitions smoothly at 7:45 AM and 5:00 PM
- Settings overlay should work identically on both backgrounds - no special logic needed
- Do not remove any existing functionality from ClockApp
- The time-based logic is completely independent of system dark mode
- Background color changes are the main visual change users will see
