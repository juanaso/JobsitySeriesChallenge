package com.juanasoapp.jobsityserieschallenge

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.juanasoapp.jobsityserieschallenge.core.idlingResource
import com.juanasoapp.jobsityserieschallenge.utils.BaseUITest
import com.juanasoapp.jobsityserieschallenge.utils.SearchViewActionExtension
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class SeriesListsFeatures : BaseUITest(){

    @Test
    fun displaySearchBar(){
        assertDisplayed(R.id.series_list_searchview)
    }

    @Test
    fun emptySeriesListOnNewSearch(){
        enterTextAndSearch()
        IdlingRegistry.getInstance().unregister(idlingResource)
        enterTextAndSearch(textToSearchDummy)
        assertRecyclerViewItemCount(R.id.series_list,0)
    }

    @Test
    fun displayListOfSearchedSeries(){
        enterTextAndSearch()

        onView(
            allOf(
                withId(R.id.series_name),
                isDescendantOfA(nthChildOf(withId(R.id.series_list),0))
            )
        )
            .check(matches(withText(seresTitleDummy)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysListOfSeries(){
        assertRecyclerViewItemCount(R.id.series_list,240)

        onView(
            allOf(
                withId(R.id.series_name),
                isDescendantOfA(nthChildOf(withId(R.id.series_list),0))
            )
        )
            .check(matches(withText(seresTitleDummy)))
            .check(matches(isDisplayed()))

    }

    @Test
    fun displayLoaderWhileFetchingShows(){
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.series_list_loader)
    }

    @Test
    fun hidesLoader(){
        assertNotDisplayed(R.id.series_list_loader)
    }

    @Test
    fun navigateToSeriesDetailScreen(){
        onView(
            allOf(
                withId(R.id.series_name),
                isDescendantOfA(nthChildOf(withId(R.id.series_list),0))
            )
        ).perform(click())
        assertDisplayed(R.id.series_detail_root)

    }

    private fun enterTextAndSearch(textToSearch:String=seresTitleDummy ) {
        onView(withId(R.id.series_list_searchview)).perform(click())
        onView(withId(R.id.series_list_searchview))
            .perform(SearchViewActionExtension.typeText(textToSearch))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
    }

}