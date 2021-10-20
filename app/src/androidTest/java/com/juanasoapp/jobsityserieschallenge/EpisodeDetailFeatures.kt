package com.juanasoapp.jobsityserieschallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.juanasoapp.jobsityserieschallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class EpisodeDetailFeatures:BaseUITest() {

    @Test
    fun displaysEpisodeNameAndDetails() {
        NavigateToDetail()
        Thread.sleep(1000)
        onView(withId(R.id.series_detail_root))
            .perform(swipeUp())

        onView(ViewMatchers.hasSibling(ViewMatchers.withText("season 1"))).perform(ViewActions.click())
        onView(ViewMatchers.hasSibling(ViewMatchers.withText("1 - Pilot"))).perform(ViewActions.click())


        assertDisplayed("Pilot")
        assertDisplayed("Episode 1")
        assertDisplayed("Season 1")
    }
}