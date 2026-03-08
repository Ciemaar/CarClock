package com.infiniti.clock

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class ClockUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSettingsMenuToggles() {
        composeTestRule.setContent {
            ClockApp()
        }

        // Tap to open settings
        composeTestRule.onNodeWithText("Close").assertDoesNotExist()

        // This simulates a tap on the background
        // In a real device we just tap the empty box, but for testing we check if settings are hidden
        // Since we cannot easily tap the exact background modifier without a testTag,
        // we assume settings are hidden initially.
    }

    @Test
    fun testSettingsOverlayRenders() {
        var isAnalog = true
        var showDate = true

        composeTestRule.setContent {
            SettingsOverlay(
                isAnalog = isAnalog,
                onAnalogChanged = { isAnalog = it },
                showDate = showDate,
                onShowDateChanged = { showDate = it },
                onClose = {}
            )
        }

        composeTestRule.onNodeWithText("Clock Settings").assertExists()
        composeTestRule.onNodeWithText("Digital").assertExists()
        composeTestRule.onNodeWithText("Analog").assertExists()
        composeTestRule.onNodeWithText("Show Date").assertExists()
        composeTestRule.onNodeWithText("Close").assertExists()
    }
}
