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

        // Because AnalogClock uses an infinite LaunchedEffect loop, waitForIdle() will hang.
        // We pause the main clock to allow the test to assert its state safely.
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(500)
    }
}
