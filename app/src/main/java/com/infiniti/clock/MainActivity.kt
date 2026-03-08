package com.infiniti.clock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.infiniti.clock.ui.theme.InfinitiClockTheme

/**
 * The main entry point for the Infiniti Clock Android Automotive application.
 * This activity handles setting up the Jetpack Compose environment and applying the app theme.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfinitiClockTheme {
                ClockApp()
            }
        }
    }
}

/**
 * The root composable for the application.
 * It manages the high-level state (such as whether the clock is analog/digital,
 * and if the date is showing) and handles user interactions (like tapping the screen
 * to bring up the [SettingsOverlay]).
 */
@Composable
fun ClockApp() {
    var showSettings by remember { mutableStateOf(false) }
    var isAnalog by remember { mutableStateOf(true) }
    var showDate by remember { mutableStateOf(true) }

    val backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color(0xFFF5F5F5)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable { showSettings = !showSettings },
        contentAlignment = Alignment.Center
    ) {
        if (isAnalog) {
            AnalogClock(showDate = showDate)
        } else {
            DigitalClock(showDate = showDate)
        }

        if (showSettings) {
            SettingsOverlay(
                isAnalog = isAnalog,
                onAnalogChanged = { isAnalog = it },
                showDate = showDate,
                onShowDateChanged = { showDate = it },
                onClose = { showSettings = false }
            )
        }
    }
}
