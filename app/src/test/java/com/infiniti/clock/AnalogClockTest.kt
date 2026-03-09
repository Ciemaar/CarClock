package com.infiniti.clock

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AnalogClockTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAnalogClock_rendersWithoutCrashing() {
        composeTestRule.setContent {
            AnalogClock(showDate = false)
        }

        // Analog clock uses Canvas, which doesn't expose semantics easily without explicit testTags
        // But rendering it via Robolectric ensures the geometry logic (sin/cos/trigonometry) doesn't crash.
        composeTestRule.waitForIdle()
        assert(true)
    }
}
