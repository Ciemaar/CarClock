package com.infiniti.clock

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingsOverlayTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSettingsOverlay_buttonsWork() {
        var analogState = true
        var dateState = true
        var isClosed = false

        composeTestRule.setContent {
            SettingsOverlay(
                isAnalog = analogState,
                onAnalogChanged = { analogState = it },
                showDate = dateState,
                onShowDateChanged = { dateState = it },
                onClose = { isClosed = true }
            )
        }

        // Check if labels render
        composeTestRule.onNodeWithText("Clock Settings").assertExists()
        composeTestRule.onNodeWithText("Digital").assertExists()
        composeTestRule.onNodeWithText("Analog").assertExists()

        // Toggle the analog state
        composeTestRule.onNodeWithText("Analog").performClick()

        // Click Close
        composeTestRule.onNodeWithText("Close").performClick()
        assert(isClosed)
    }
}
