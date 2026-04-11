package com.infiniti.clock

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityRobolectricTest {

    @Test
    fun testMainActivityLifecycle() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        // Ensure the activity launches and stays resumed without crashing
        assert(scenario.state == Lifecycle.State.RESUMED)
        scenario.close()
    }
}
