# Unit 5: Create Comprehensive Unit Tests

## Objective
Write comprehensive unit and integration tests to verify the time-based theming system works correctly. Tests should cover boundary conditions, color accuracy, and integration between components.

## Context
- **Issue**: #4 - Make the background for the clock sky blue during the day and black at night
- **Plan**: See `plans/04-time-based-background-colors.md` for full implementation plan
- **Part of**: Issue #4 Implementation (Unit 5 of 5)
- **Dependencies**: Units 1-4 should be completed before this unit

## Requirements

### Files to Create/Modify

**Create:**
- `app/src/test/java/com/infiniti/clock/util/TimeBasedThemeUtilTest.kt`

**Modify:**
- `app/src/test/java/com/infiniti/clock/MainActivityTest.kt` - Add background color tests
- Other test files as needed for updated composables

### Acceptance Criteria

#### TimeBasedThemeUtilTest
- [ ] Test `isDayTime()` with Calendar at 7:44:59 AM → returns false
- [ ] Test `isDayTime()` with Calendar at 7:45:00 AM → returns true
- [ ] Test `isDayTime()` with Calendar at 12:00:00 PM → returns true
- [ ] Test `isDayTime()` with Calendar at 4:59:59 PM → returns true
- [ ] Test `isDayTime()` with Calendar at 5:00:00 PM → returns false
- [ ] Test `isDayTime()` with Calendar at 11:59:59 PM → returns false
- [ ] Test `isDayTime()` with Calendar at 00:00:00 AM → returns false
- [ ] Test `isDayTime()` with Calendar at 3:00:00 AM → returns false
- [ ] Test `getDayBackgroundColor()` returns `Color(0xFF5B9BD5)`
- [ ] Test `getNightBackgroundColor()` returns `Color.Black`
- [ ] Test `getDayClockColor()` returns `Color.Black`
- [ ] Test `getNightClockColor()` returns `Color.White`
- [ ] Test `getBackgroundColor(true)` returns day color
- [ ] Test `getBackgroundColor(false)` returns night color
- [ ] Test `getClockColor(true)` returns day color
- [ ] Test `getClockColor(false)` returns night color
- [ ] All tests run on JVM (no Robolectric required)
- [ ] 100% code coverage for TimeBasedThemeUtil

#### MainActivityTest Updates
- [ ] Test `ClockApp()` background color at 9 AM (should be sky blue)
- [ ] Test `ClockApp()` background color at 9 PM (should be black)
- [ ] Test `ClockApp()` background color at exact 7:45:00 AM (transition point)
- [ ] Test `ClockApp()` background color at exact 5:00:00 PM (transition point)
- [ ] Test settings button is visible on day background
- [ ] Test settings button is visible on night background
- [ ] Test settings button is clickable on day background
- [ ] Test settings button is clickable on night background
- [ ] All existing MainActivityTest tests still pass

#### Overall Project Coverage
- [ ] Overall test coverage remains at 75% minimum
- [ ] All new/modified code is tested
- [ ] No uncovered critical paths

### Test Implementation Guide

#### TimeBasedThemeUtilTest Structure
```kotlin
@RunWith(RobolectricTestRunner::class)
class TimeBasedThemeUtilTest {
    
    @Test
    fun isDayTime_before745Am_returnsFalse() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 44)
            set(Calendar.SECOND, 59)
        }
        assertFalse(TimeBasedThemeUtil.isDayTime(calendar))
    }
    
    @Test
    fun isDayTime_at745Am_returnsTrue() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 45)
            set(Calendar.SECOND, 0)
        }
        assertTrue(TimeBasedThemeUtil.isDayTime(calendar))
    }
    
    // Additional boundary tests...
    
    @Test
    fun getDayBackgroundColor_returnsSkylue() {
        val color = TimeBasedThemeUtil.getDayBackgroundColor()
        assertEquals(Color(0xFF5B9BD5), color)
    }
    
    // Color tests...
}
```

#### MainActivityTest Structure
```kotlin
@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun clockAppBackground_atDayTime_isSkyBlue() {
        val dayTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 30)
        }
        
        composeTestRule.setContent {
            // Mock the time somehow or use TimeBasedThemeUtil directly
            val isDayTime = TimeBasedThemeUtil.isDayTime(dayTime)
            assertEquals(true, isDayTime)
        }
    }
    
    @Test
    fun clockAppBackground_atNightTime_isBlack() {
        val nightTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 22)
            set(Calendar.MINUTE, 30)
        }
        
        composeTestRule.setContent {
            val isDayTime = TimeBasedThemeUtil.isDayTime(nightTime)
            assertEquals(false, isDayTime)
        }
    }
    
    @Test
    fun settingsButton_visibleAndClickable_onBothBackgrounds() {
        composeTestRule.setContent {
            ClockApp()
        }
        
        // Find settings button
        composeTestRule.onNodeWithTag("settings_button")
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        
        // Verify settings opened
    }
}
```

### Edge Case Testing
- Exact minute boundaries (7:45:00 vs 7:44:59)
- Exact hour boundaries (5:00:00 vs 4:59:59)
- Midnight (00:00:00)
- Noon (12:00:00)
- Random times throughout the day

### Parameterized Testing (Optional but Recommended)
Use JUnit parameterized tests to test multiple time values efficiently:

```kotlin
@RunWith(Parameterized::class)
class TimeBasedThemeUtilParameterizedTest(
    val hourOfDay: Int,
    val minute: Int,
    val expectedDayTime: Boolean
) {
    
    @Test
    fun isDayTime_variousTimes() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        assertEquals(expectedDayTime, TimeBasedThemeUtil.isDayTime(calendar))
    }
    
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(6, 0, false),   // 6:00 AM
            arrayOf(7, 44, false),  // 7:44 AM
            arrayOf(7, 45, true),   // 7:45 AM
            arrayOf(12, 0, true),   // 12:00 PM
            arrayOf(16, 59, true),  // 4:59 PM
            arrayOf(17, 0, false),  // 5:00 PM
            arrayOf(23, 0, false),  // 11:00 PM
        )
    }
}
```

### Verification Commands
```bash
# Run all tests
./gradlew testDebugUnitTest

# Check coverage
./gradlew koverVerifyDebug

# View coverage report
./gradlew koverHtmlReportDebug  # Check build/reports/kover/index.html

# Run specific test class
./gradlew testDebugUnitTest --tests TimeBasedThemeUtilTest

# Run formatting and analysis
./gradlew spotlessCheck
./gradlew detekt
./gradlew dokkaHtml
```

### Manual Validation
1. Run app in emulator
2. Set emulator time to 7:44 AM - verify background is black (night)
3. Set emulator time to 7:45 AM - verify background is sky blue (day)
4. Set emulator time to 4:59 PM - verify background is sky blue (day)
5. Set emulator time to 5:00 PM - verify background is black (night)
6. Verify settings overlay works on both backgrounds

## Notes
- TimeBasedThemeUtilTest should use pure JVM tests (no Robolectric)
- MainActivityTest can use Robolectric for Compose testing
- Parameterized tests make boundary testing more concise
- Test both the utility functions AND the integration in composables
- Coverage target is 75% for the entire project
- Consider testing color contrast ratios for accessibility
