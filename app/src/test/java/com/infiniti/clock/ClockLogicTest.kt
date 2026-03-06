package com.infiniti.clock

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ClockLogicTest {

    @Test
    fun testTimeFormatting() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 14) // 2 PM
        calendar.set(Calendar.MINUTE, 30)
        val time: Date = calendar.time

        val timeFormat = SimpleDateFormat("h:mm", Locale.getDefault())
        val amPmFormat = SimpleDateFormat("a", Locale.getDefault())

        assertEquals("2:30", timeFormat.format(time))
        assertEquals("PM", amPmFormat.format(time))
    }

    @Test
    fun testDateFormatting() {
        val calendar = Calendar.getInstance()
        calendar.set(2026, Calendar.JANUARY, 1)
        val time: Date = calendar.time

        val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())

        assertEquals("Thursday, January 1", dateFormat.format(time))
    }
}
