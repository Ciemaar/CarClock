package com.infiniti.clock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infiniti.clock.ui.theme.InfinitiGreen
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Renders an analog clock face using the Compose [Canvas].
 * The clock ticks every second using a coroutine launched via [LaunchedEffect].
 *
 * @param showDate Boolean indicating whether the [DateDisplay] should be rendered below the clock.
 */
@Composable
fun AnalogClock(showDate: Boolean) {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance()
            delay(1000)
        }
    }

    val isDark = isSystemInDarkTheme()
    val clockColor = if (isDark) Color.White else Color.Black
    val handColor = if (isDark) Color.LightGray else Color.DarkGray
    val accentColor = if (isDark) Color.White else InfinitiGreen

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(300.dp)) {
            val radius = size.minDimension / 2
            val center = Offset(size.width / 2, size.height / 2)

            // Draw clock face outline
            drawCircle(
                color = clockColor,
                radius = radius,
                center = center,
                style = Stroke(width = 4.dp.toPx()),
            )

            // Draw tick marks
            for (i in 0 until 60) {
                val angleInDegrees = i * 6f
                val lineLength = if (i % 5 == 0) 15.dp.toPx() else 8.dp.toPx()
                val lineThickness = if (i % 5 == 0) 3.dp.toPx() else 1.dp.toPx()

                rotate(angleInDegrees) {
                    drawLine(
                        color = clockColor,
                        start = Offset(center.x, center.y - radius + lineLength),
                        end = Offset(center.x, center.y - radius),
                        strokeWidth = lineThickness,
                        cap = StrokeCap.Round,
                    )
                }
            }

            val hour = currentTime.get(Calendar.HOUR)
            val minute = currentTime.get(Calendar.MINUTE)
            val second = currentTime.get(Calendar.SECOND)

            // Draw Hour Hand (30 degrees per hour, plus minute fraction)
            rotate(hour * 30f + minute * 0.5f) {
                drawLine(
                    color = handColor,
                    start = center,
                    end = Offset(center.x, center.y - radius * 0.5f),
                    strokeWidth = 6.dp.toPx(),
                    cap = StrokeCap.Round,
                )
            }

            // Draw Minute Hand (6 degrees per minute, plus second fraction)
            rotate(minute * 6f + second * 0.1f) {
                drawLine(
                    color = handColor,
                    start = center,
                    end = Offset(center.x, center.y - radius * 0.75f),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round,
                )
            }

            // Draw Second Hand (6 degrees per second)
            rotate(second * 6f) {
                drawLine(
                    color = accentColor,
                    start = center,
                    end = Offset(center.x, center.y - radius * 0.85f),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round,
                )
            }

            // Draw Center Dot
            drawCircle(
                color = accentColor,
                radius = 6.dp.toPx(),
                center = center,
            )
        }

        if (showDate) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 180.dp),
            ) {
                DateDisplay()
            }
        }
    }
}

/**
 * Renders a large, modern digital clock format.
 * The time updates continuously via a [LaunchedEffect] coroutine.
 *
 * @param showDate Boolean indicating whether the [DateDisplay] should be rendered below the clock.
 */
@Composable
fun DigitalClock(showDate: Boolean) {
    var currentTime by remember { mutableStateOf(Date()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Date()
            delay(1000)
        }
    }

    val timeFormat = SimpleDateFormat("h:mm", Locale.getDefault())
    val amPmFormat = SimpleDateFormat("a", Locale.getDefault())

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = timeFormat.format(currentTime),
            fontSize = 96.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = amPmFormat.format(currentTime),
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.secondary,
        )

        if (showDate) {
            Spacer(modifier = Modifier.height(16.dp))
            DateDisplay()
        }
    }
}

/**
 * A sub-component that formats and displays the current day of the week, month, and date.
 * Updates automatically every minute to ensure the date remains accurate if left running overnight.
 */
@Composable
fun DateDisplay() {
    var currentDate by remember { mutableStateOf(Date()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentDate = Date()
            delay(60000) // update every minute
        }
    }

    val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())

    Text(
        text = dateFormat.format(currentDate),
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.secondary,
    )
}
