package com.juanasoapp.jobsityserieschallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.juanasoapp.jobsityserieschallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class SeriesDetailFeatures:BaseUITest() {

    private val seriesDetailWeekTimeDummy = "Thursday"
    private val seriesDetailAirTimeDummy = "22:00"
    private val seriesDetailGenreDummy = "Drama - Science-Fiction - Thriller"

    @Test
    fun displaysSeriesNameAndDetails() {
        NavigateToDetail()

        onView(withId(R.id.series_detail_root))
            .perform(swipeUp())

        assertDisplayed(seresTitleDummy)
        assertDisplayed(seriesDetailWeekTimeDummy)
        assertDisplayed(seriesDetailAirTimeDummy)
        assertDisplayed(seriesDetailGenreDummy)
    }

    @Test
    fun getListOfEpisodesFromRepository(){
        NavigateToDetail()

        onView(
            withId(R.id.linear_seasons_container)
        ).check(matches(withChildViewCount(3)))
    }

    @Test
    fun navigateToEpisodeDetailScreen(){
        NavigateToDetail()
        Thread.sleep(1000)
        onView(withId(R.id.series_detail_root))
            .perform(swipeUp())

        onView(hasSibling(withText(seriesDetailSeasonDummy))).perform(click())
        onView(hasSibling(withText(seriesDetailEpisodeTitle))).perform(click())

        assertDisplayed(R.id.episode_detail_root)
    }
}