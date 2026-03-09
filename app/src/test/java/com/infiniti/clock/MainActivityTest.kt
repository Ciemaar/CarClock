package com.infiniti.clock

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMainActivity_togglesBetweenAnalogAndDigital() {
        composeTestRule.setContent {
            ClockApp()
        }

        // Settings are hidden by default
        composeTestRule.onNodeWithText("Clock Settings").assertDoesNotExist()

        // Click the background to open the menu
        composeTestRule.onNodeWithTag("ClockBackground").performClick()
        composeTestRule.onNodeWithText("Clock Settings").assertExists()

        // Toggle to Digital
        composeTestRule.onNodeWithText("Digital").performClick()

        // Toggle Date Off
        composeTestRule.onNodeWithText("Off").performClick()

        // Close menu
        composeTestRule.onNodeWithText("Close").performClick()
        composeTestRule.onNodeWithText("Clock Settings").assertDoesNotExist()
    }
}
