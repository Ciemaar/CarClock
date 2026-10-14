# Unit 1: Create TimeBasedThemeUtil Utility Module

## Objective
Create a utility module that provides time-based color logic for the clock application. This module should determine whether it's "day" or "night" based on a fixed schedule (7:45 AM - 5:00 PM) and return appropriate colors.

## Context
- **Issue**: #4 - Make the background for the clock sky blue during the day and black at night
- **Plan**: See `plans/04-time-based-background-colors.md` for full implementation plan
- **Part of**: Issue #4 Implementation (Unit 1 of 5)

## Requirements

### File to Create
Create: `app/src/main/java/com/infiniti/clock/util/TimeBasedThemeUtil.kt`

### Acceptance Criteria
- [ ] `isDayTime(calendar: Calendar): Boolean` function returns true for 7:45 AM - 4:59:59 PM
- [ ] `isDayTime()` returns false for 5:00 PM - 7:44:59 AM
- [ ] Edge cases handled: 7:45:00 AM (inclusive), 5:00:00 PM (exclusive)
- [ ] `getDayBackgroundColor(): Color` returns sky blue (`Color(0xFF5B9BD5)`)
- [ ] `getNightBackgroundColor(): Color` returns black (`Color.Black`)
- [ ] `getDayClockColor(): Color` returns black (`Color.Black`)
- [ ] `getNightClockColor(): Color` returns white (`Color.White`)
- [ ] All functions are pure (no side effects, no Android dependencies)
- [ ] KDoc comments explain day/night boundary logic and purpose
- [ ] Function is easily mockable for testing
- [ ] Code passes spotlessCheck, detekt, and dokkaHtml

### Implementation Details

The utility should be a Kotlin `object` (singleton) with the following structure:

```kotlin
/**
 * Time-based theme utility for managing day/night colors in the clock application.
 * 
 * Day is defined as 7:45 AM to 5:00 PM regardless of actual sunrise/sunset times.
 * This provides consistent automotive-friendly behavior for the dashboard display.
 */
object TimeBasedThemeUtil {
    
    /**
     * Determines if the current time should be considered "day" for color purposes.
     * Day period: 7:45 AM (inclusive) to 5:00 PM (exclusive)
     * 
     * @param calendar The calendar representing the time to check
     * @return true if between 7:45 AM and 4:59:59 PM, false otherwise
     */
    fun isDayTime(calendar: Calendar): Boolean {
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        
        // Before 7 AM: night
        if (hourOfDay < 7) return false
        
        // At 7 AM but before 7:45 AM: night
        if (hourOfDay == 7 && minute < 45) return false
        
        // At 5 PM or later: night
        if (hourOfDay >= 17) return false
        
        // Between 7:45 AM and 4:59:59 PM: day
        return true
    }
    
    /**
     * Returns the background color for day mode.
     * 
     * @return Automotive-friendly sky blue (`Color(0xFF5B9BD5)`)
     */
    fun getDayBackgroundColor(): Color = Color(0xFF5B9BD5)
    
    /**
     * Returns the background color for night mode.
     * 
     * @return Black (`Color.Black`)
     */
    fun getNightBackgroundColor(): Color = Color.Black
    
    /**
     * Returns the clock outline/hand color for day mode.
     * 
     * @return Black for contrast on sky blue background
     */
    fun getDayClockColor(): Color = Color.Black
    
    /**
     * Returns the clock outline/hand color for night mode.
     * 
     * @return White for contrast on black background
     */
    fun getNightClockColor(): Color = Color.White
    
    /**
     * Convenience function to get background color based on day/night status.
     * 
     * @param isDayTime whether it is currently day time
     * @return Sky blue if day, black if night
     */
    fun getBackgroundColor(isDayTime: Boolean): Color =
        if (isDayTime) getDayBackgroundColor() else getNightBackgroundColor()
    
    /**
     * Convenience function to get clock color based on day/night status.
     * 
     * @param isDayTime whether it is currently day time
     * @return Black if day, white if night
     */
    fun getClockColor(isDayTime: Boolean): Color =
        if (isDayTime) getDayClockColor() else getNightClockColor()
}
```

### Testing Approach
- Create `app/src/test/java/com/infiniti/clock/util/TimeBasedThemeUtilTest.kt`
- Use JUnit with Calendar mocking
- Test boundary conditions (7:44:59 AM, 7:45:00 AM, 4:59:59 PM, 5:00:00 PM, etc.)
- No Robolectric needed (pure JVM tests)
- Target: 100% code coverage for this utility

### Verification
Run the following commands to verify:
```bash
./gradlew spotlessCheck     # Verify formatting
./gradlew detekt            # Run static analysis
./gradlew dokkaHtml         # Verify KDoc documentation
./gradlew testDebugUnitTest # Run unit tests
./gradlew koverVerifyDebug  # Verify coverage (75% minimum for project)
```

## Notes
- This is a pure utility with no Android dependencies, making it highly testable
- The calendar parameter allows easy mocking in tests
- Colors are hardcoded to avoid Material3 theme complexities
- The fixed 7:45 AM - 5:00 PM schedule is intentional per issue requirements
