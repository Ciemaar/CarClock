package com.infiniti.clock.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDarkThemeColors() {
        composeTestRule.setContent {
            InfinitiClockTheme(darkTheme = true) {
                assertEquals(androidx.compose.ui.graphics.Color.White, MaterialTheme.colorScheme.primary)
                assertEquals(androidx.compose.ui.graphics.Color.Black, MaterialTheme.colorScheme.background)
            }
        }
    }

    @Test
    fun testLightThemeColors() {
        composeTestRule.setContent {
            InfinitiClockTheme(darkTheme = false) {
                assertEquals(androidx.compose.ui.graphics.Color.Black, MaterialTheme.colorScheme.primary)
                assertEquals(androidx.compose.ui.graphics.Color(0xFFF0F0F0), MaterialTheme.colorScheme.background)
            }
        }
    }
}
