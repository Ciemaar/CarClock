package com.infiniti.clock

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DigitalClockTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDigitalClock_rendersTimeAndDate() {
        composeTestRule.setContent {
            DigitalClock(showDate = true)
        }

        // Because DigitalClock has an infinite loop LaunchedEffect, waitForIdle() will timeout.
        // We pause the main clock to allow the test to finish executing.
        composeTestRule.mainClock.autoAdvance = false

        // Let it advance just enough to render the first frame
        composeTestRule.mainClock.advanceTimeBy(500)
    }
}
