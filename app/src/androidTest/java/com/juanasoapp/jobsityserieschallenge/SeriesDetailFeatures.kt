package com.juanasoapp.jobsityserieschallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.juanasoapp.jobsityserieschallenge.serieslist.idlingResource
import com.juanasoapp.jobsityserieschallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class SeriesDetailFeatures:BaseUITest() {

    /*
○ List of episodes separated by season
     */

    @Test
    fun displaysSeriesNameAndDetails() {
        NavigateToDetail()

        assertDisplayed("Under the Dome")
        assertDisplayed("Thursday")
        assertDisplayed("22:00")
        assertDisplayed("Drama - Science-Fiction - Thriller")
    }

    @Test
    fun getListOfEpisodesFromRepository(){
        NavigateToDetail()

        onView(
            withId(R.id.linear_seasons_container)
        ).check(matches(withChildViewCount(3)))
    }

    @Test
    fun displayLoaderWhileFetchingEpisodes(){
        NavigateToDetail()
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.series_detail_loader)
    }

    private fun NavigateToDetail() {
        onView(
            allOf(
                withId(R.id.series_name),
                isDescendantOfA(nthChildOf(withId(R.id.series_list), 0))
            )
        ).perform(ViewActions.click())
    }


}