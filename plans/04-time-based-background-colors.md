# IMPLEMENTATION PLAN: GitHub Issue #4 - Time-Based Background and Clock Hand Colors

**Status:** `draft`  
**Issue:** #4  
**Created:** 2026-10-14  
**Updated:** 2026-10-14  

---

## 1. ANALYSIS

**Current State:**
- Background color is determined by `isSystemDarkTheme()` in `ClockApp()` (MainActivity.kt line 51)
- Light mode uses `Color(0xFFF5F5F5)` (light gray), dark mode uses `Color.Black`
- Clock colors in `AnalogClock()` are also based on system dark theme (ClockComponents.kt lines 53-56)
- Theme system in Theme.kt uses Material3 with standard dark/light color schemes
- No time-based logic currently exists in the codebase

**Affected Composables:**
1. `ClockApp()` - Sets background and passes showDate state
2. `AnalogClock()` - Draws clock face, hands, tick marks with colors
3. `DigitalClock()` - Uses MaterialTheme colors for text (less impacted)
4. Theme.kt - Global color scheme

**Current Color Behavior:**
- Light mode: Black outline/hands on light gray background
- Dark mode: White outline/hands on black background
- System sensor determines theme, not actual time of day

**Required Changes:**
Replace system theme-based colors with time-based colors:
- 7:45 AM - 5:00 PM: Sky blue background, black text/hands
- 5:00 PM - 7:45 AM: Black background, white text/hands

---

## 2. APPROACH

**Design Principles:**
1. **Separation of Concerns**: Create a dedicated utility module for time-based logic, keeping it independent from UI layer
2. **Testability**: Use pure functions that accept Calendar objects to enable easy unit testing with mocked times
3. **Consistency**: Apply the same day/night logic to both analog and digital clocks
4. **Automotive Safety**: Ensure colors maintain sufficient contrast for dashboard glare conditions per Driver Distraction Guidelines

**Architecture Decision:**
- Create `TimeBasedThemeUtil.kt` - A singleton object with pure functions
- Functions will be time-aware but UI-agnostic
- Inject Calendar dependency for testability
- Use Color constants defined in Theme.kt for consistency
- Avoid modifying the Material3 theme directly; use these colors at the Compose level

**Time Boundary Logic:**
```
Day period: 7:45 AM (07:45:00) to 5:00 PM (17:00:00)
- isDayTime() checks if HOUR_OF_DAY >= 7 AND (HOUR_OF_DAY < 17 OR (HOUR_OF_DAY == 7 AND MINUTE >= 45))
- Handles edge case at 7:45 AM boundary
```

**Color Palette:**
- Sky blue (day background): `Color(0xFF5B9BD5)` - automotive-friendly, muted sky tone
- Day hand/outline: `Color.Black`
- Night background: `Color.Black`
- Night hand/outline: `Color.White`
- Accent colors (second hand, center dot): Preserve `InfinitiGreen` or adjust based on background

---

## 3. UNIT PROMPTS

### Unit 1: Create TimeBasedThemeUtil Utility Module

**File to Create:** `app/src/main/java/com/infiniti/clock/util/TimeBasedThemeUtil.kt`

**Acceptance Criteria:**
- `isDayTime(calendar: Calendar): Boolean` returns true for 7:45 AM - 4:59:59 PM
- `isDayTime()` returns false for 5:00 PM - 7:44:59 AM
- Edge cases handled: 7:45:00 AM (inclusive), 5:00:00 PM (exclusive)
- `getDayBackgroundColor(): Color` returns sky blue
- `getNightBackgroundColor(): Color` returns black
- `getDayClockColor(): Color` returns black
- `getNightClockColor(): Color` returns white
- All functions are pure (no side effects)
- KDoc comments explain day/night boundary logic
- Function is mockable for testing

**Implementation Approach:**
Pure utility functions without dependencies on Android context. Use Calendar parameter to enable time mocking in tests.

---

### Unit 2: Update Theme.kt with Sky Blue Color Constant

**File to Modify:** `app/src/main/java/com/infiniti/clock/ui/theme/Theme.kt`

**Acceptance Criteria:**
- `DaySkyBlue` color constant added (value: `Color(0xFF5B9BD5)`)
- Color is accessible from other modules
- KDoc explains purpose: "Automotive-friendly sky blue for daytime clock background"
- No changes to existing theme logic (material3 dark/light schemes remain)
- All existing tests still pass

---

### Unit 3: Update AnalogClock Composable to Use Time-Based Colors

**File to Modify:** `app/src/main/java/com/infiniti/clock/ClockComponents.kt`

**Acceptance Criteria:**
- `AnalogClock()` gets current Calendar inside LaunchedEffect loop
- Clock outline and tick marks are black during day, white at night
- Hour/minute hands are black during day, white at night
- Second hand and center dot use appropriate accent color based on background
- Colors update every second as time updates (no state lag)
- No system theme checks remain in AnalogClock
- Comments explain color logic
- All AnalogClock tests still pass or updated

---

### Unit 4: Update ClockApp Composable Background and DigitalClock Colors

**File to Modify:** `app/src/main/java/com/infiniti/clock/MainActivity.kt`

**Acceptance Criteria:**
- `ClockApp()` background changes based on time, not system theme
- Background is sky blue 7:45 AM - 5:00 PM
- Background is black 5:00 PM - 7:45 AM
- `DigitalClock()` text color updates based on time (if not using MaterialTheme)
- No `isSystemDarkTheme()` calls remain in ClockApp
- Settings overlay remains visible and functional over time-based background
- All MainActivityTest tests pass

---

### Unit 5: Create Comprehensive Unit Tests

**Files to Create/Modify:**
- Create: `TimeBasedThemeUtilTest.kt`
- Modify: Update existing tests to handle time-based logic

**Acceptance Criteria:**
- Test `isDayTime()` with Calendar at 7:44:59 AM → returns false
- Test `isDayTime()` with Calendar at 7:45:00 AM → returns true
- Test `isDayTime()` with Calendar at 12:00:00 PM → returns true
- Test `isDayTime()` with Calendar at 4:59:59 PM → returns true
- Test `isDayTime()` with Calendar at 5:00:00 PM → returns false
- Test `isDayTime()` with Calendar at 11:59:59 PM → returns false
- Test color functions return correct Color objects
- Test day/night color contrast is sufficient (WCAG AA minimum)
- All tests pass with 75%+ code coverage

---

## 4. FILE CHANGES SUMMARY

**Files to Create:**
1. `app/src/main/java/com/infiniti/clock/util/TimeBasedThemeUtil.kt`
2. `app/src/test/java/com/infiniti/clock/util/TimeBasedThemeUtilTest.kt`

**Files to Modify:**
1. `app/src/main/java/com/infiniti/clock/ui/theme/Theme.kt` - Add DaySkyBlue color constant
2. `app/src/main/java/com/infiniti/clock/MainActivity.kt` - Update ClockApp() background logic
3. `app/src/main/java/com/infiniti/clock/ClockComponents.kt` - Update AnalogClock() colors
4. `app/src/test/java/com/infiniti/clock/MainActivityTest.kt` - Add/update tests

---

## 5. TESTING STRATEGY

**Unit Testing (TimeBasedThemeUtil):**
- JVM-based pure unit tests (no Robolectric required)
- Calendar mocking with specific dates/times
- Test all boundary conditions (7:44:59 AM, 7:45:00 AM, etc.)
- Test color return values match constants
- Target: 100% code coverage for util module

**Compose UI Testing:**
- Use Robolectric for local Compose UI testing
- Mock time by injecting Calendar into composables
- Render `AnalogClock` at fixed times and verify colors
- Render `ClockApp` background and verify color changes
- Test that manual clock updates trigger color recomposition
- Use `composeTestRule.mainClock.advanceTimeBy()` to simulate time passage

**Integration Testing:**
- `MainActivityTest` verifies background changes when settings are toggled
- Test that all 4 combinations work (Day/Night × Analog/Digital)
- Verify settings overlay remains accessible regardless of background color

**Edge Cases:**
- Exact moment of 7:45:00 AM (day boundary start)
- Exact moment of 5:00:00 PM (day boundary end)
- Midnight (00:00:00)
- Second hand position near boundary (doesn't cause color flicker)

**Coverage Requirements:**
- TimeBasedThemeUtil: 100% line coverage
- Updated composables: Maintain 75%+ overall project coverage
- Run: `./gradlew koverVerifyDebug` to validate

---

## 6. RISK ASSESSMENT & AUTOMOTIVE CONSTRAINTS

**Technical Risks:**

1. **Color Contrast in Bright Sunlight**
   - Risk: Sky blue background may not be sufficiently visible in bright daylight
   - Mitigation: Choose muted sky blue (0xFF5B9BD5) with better contrast
   - Validation: Visual test on real automotive display or high-brightness emulator

2. **Continuous Time Checks Impact Performance**
   - Risk: Calling `isDayTime()` every frame could cause micro-stutters
   - Mitigation: Time check is O(1); Calendar.getInstance() is cached; no performance issue expected
   - Validation: Run profiler to confirm frame time < 16ms (60 fps)

3. **Timezone Handling**
   - Current Limitation: Requirement specifies fixed 7:45 AM - 5:00 PM regardless of locale
   - Implementation: Uses local time zone (Calendar.getInstance())

4. **Recomposition Frequency**
   - Risk: Updating currentTime every 1 second may cause unnecessary recomposition
   - Mitigation: State change only triggers recomposition if color actually changes
   - Validation: Profile recomposition rate

**Automotive Constraints:**

1. **Driver Distraction Guidelines (DDG)**
   - Compliance: Background color change is passive, not distracting
   - Glanceability preserved: Clock remains simple to read in day/night modes

2. **Dashboard Glare**
   - Day (sky blue): Lighter color with muted tone and black text contrast
   - Night (black): Standard for nighttime dashboards; white text provides contrast
   - Testing: Verify with automotive emulator brightness settings

3. **Color Blindness**
   - Black/white/blue palette is accessible to most color blind users
   - No reliance on color alone for critical information

4. **Android Automotive OS Behavior**
   - No special requirements: Uses only standard Compose and Calendar APIs
   - No changes required to AndroidManifest.xml
   - Can coexist with headlight sensor integration

---

## 7. IMPLEMENTATION SEQUENCE

**Phase 1: Core Utility (Day 1)**
1. Create `TimeBasedThemeUtil.kt` with `isDayTime()` and color functions
2. Create `TimeBasedThemeUtilTest.kt` with comprehensive tests
3. Update `Theme.kt` to add `DaySkyBlue` constant
4. Verify spotlessCheck, detekt, dokkaHtml pass

**Phase 2: Analog Clock Updates (Day 2)**
1. Update `AnalogClock()` in ClockComponents.kt
2. Update `AnalogClockTest.kt` as needed
3. Verify tests pass and coverage maintained

**Phase 3: Main App Updates (Day 2)**
1. Update `ClockApp()` in MainActivity.kt
2. Update `MainActivityTest.kt` with color verification tests
3. Verify all existing tests still pass

**Phase 4: Validation & Polish (Day 3)**
1. Run full build: `./gradlew spotlessCheck detekt dokkaHtml lintDebug testDebugUnitTest koverVerifyDebug assembleDebug`
2. Manual testing in emulator at different times
3. Verify colors at exact boundaries (7:45 AM, 5:00 PM)
4. Code review and documentation

---

## CRITICAL FILES FOR IMPLEMENTATION

- `app/src/main/java/com/infiniti/clock/util/TimeBasedThemeUtil.kt` (CREATE)
- `app/src/main/java/com/infiniti/clock/ui/theme/Theme.kt`
- `app/src/main/java/com/infiniti/clock/MainActivity.kt`
- `app/src/main/java/com/infiniti/clock/ClockComponents.kt`
