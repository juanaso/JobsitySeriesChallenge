package com.juanasoapp.jobsityserieschallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class SeriesListsFeatures :BaseUITest(){

    @Test
    fun displayAppTitle(){
        assertDisplayed(R.string.app_display_title)
    }

    @Test
    fun displaysListOfMovies(){
        Thread.sleep(3000)

        assertRecyclerViewItemCount(R.id.series_list,240)

        onView(
            allOf(
                withId(R.id.series_name),
                isDescendantOfA(nthChildOf(withId(R.id.series_list),0))
            )
        )
            .check(matches(withText("Under the Dome")))
            .check(matches(isDisplayed()))

    }

}