package com.juanasoapp.jobsityserieschallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.juanasoapp.jobsityserieschallenge.serieslist.idlingResource
import com.juanasoapp.jobsityserieschallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import kotlinx.android.synthetic.main.fragment_series_detail.view.*
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class SeriesDetailFeatures:BaseUITest() {

    @Test
    fun displaysSeriesNameAndDetails() {
        NavigateToDetail()

        onView(withId(R.id.series_detail_root))
            .perform(swipeUp())

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



    private fun NavigateToDetail() {
        onView(
            allOf(
                withId(R.id.series_name),
                isDescendantOfA(nthChildOf(withId(R.id.series_list), 0))
            )
        ).perform(click())
    }


}