# Unit 3: Update AnalogClock Composable to Use Time-Based Colors

## Objective
Modify the AnalogClock composable to use time-based colors instead of system dark mode. The clock hands, tick marks, and outline should be black during the day and white at night.

## Context
- **Issue**: #4 - Make the background for the clock sky blue during the day and black at night
- **Plan**: See `plans/04-time-based-background-colors.md` for full implementation plan
- **Part of**: Issue #4 Implementation (Unit 3 of 5)
- **Dependencies**: Units 1-2 should be completed first

## Requirements

### File to Modify
`app/src/main/java/com/infiniti/clock/ClockComponents.kt` - the `AnalogClock()` composable

### Acceptance Criteria
- [ ] `AnalogClock()` gets current Calendar inside LaunchedEffect loop
- [ ] Clock outline (circle) is black during day, white at night
- [ ] Tick marks are black during day, white at night
- [ ] Hour hand is black during day, white at night
- [ ] Minute hand is black during day, white at night
- [ ] Second hand uses accent color (InfinitiGreen for day, white for night)
- [ ] Center dot uses accent color to match second hand
- [ ] Colors update every second as time updates (no state lag)
- [ ] No `isSystemDarkTheme()` calls remain in AnalogClock function
- [ ] Comments explain the color logic
- [ ] All AnalogClockTest tests still pass or are appropriately updated
- [ ] Code passes spotlessCheck, detekt, and dokkaHtml

### Implementation Pattern

Replace the current system theme-based color logic:

**Current approach (to be replaced)**:
```kotlin
val isDark = isSystemDarkTheme()
val clockColor = if (isDark) Color.White else Color.Black
val handColor = if (isDark) Color.LightGray else Color.DarkGray
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
val clockColor = TimeBasedThemeUtil.getClockColor(isDayTime)
val accentColor = if (isDayTime) InfinitiGreen else Color.White
```

### Imports to Add
```kotlin
import com.infiniti.clock.util.TimeBasedThemeUtil
import com.infiniti.clock.ui.theme.DaySkyBlue // If not already imported
```

### Affected Drawing Elements
Update color usage in the Canvas drawing calls for:
- `drawCircle()` for clock outline - use `clockColor`
- `drawLine()` for tick marks - use `clockColor`
- `drawLine()` or `drawPath()` for hour hand - use `clockColor`
- `drawLine()` or `drawPath()` for minute hand - use `clockColor`
- `drawLine()` for second hand - use `accentColor`
- `drawCircle()` for center dot - use `accentColor`

### Testing Approach
- Compose test with fixed Calendar times
- Render `AnalogClock()` at 9 AM and verify black colors are used
- Render `AnalogClock()` at 9 PM and verify white colors are used
- Use `composeTestRule.mainClock.advanceTimeBy()` to simulate time passage
- Test the time update loop works correctly

### Example Test Structure
```kotlin
@Test
fun testAnalogClockDayColors() {
    composeTestRule.setContent {
        AnalogClock(currentTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 30)
        })
    }
    // Verify colors using onNodeWithTag or Canvas drawing inspection
}
```

### Verification
Run the following commands:
```bash
./gradlew spotlessCheck
./gradlew detekt
./gradlew dokkaHtml
./gradlew testDebugUnitTest
./gradlew koverVerifyDebug  # Verify 75%+ coverage maintained
```

## Notes
- The continuous time update in LaunchedEffect ensures the clock hands are always current
- By using TimeBasedThemeUtil.isDayTime() with the current Calendar, the transition happens automatically
- The second hand and center dot use accent colors (not pure black/white) to maintain visual polish
- No changes needed to the canvas drawing logic itself, only the color variables
- Test manually in emulator at 7:44 AM (should show white) and 7:45 AM (should show black)
