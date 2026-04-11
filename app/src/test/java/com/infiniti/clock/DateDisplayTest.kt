package com.infiniti.clock

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DateDisplayTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDateDisplay_rendersWithoutCrashing() {
        composeTestRule.setContent {
            DateDisplay()
        }

        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(500)
    }
}
