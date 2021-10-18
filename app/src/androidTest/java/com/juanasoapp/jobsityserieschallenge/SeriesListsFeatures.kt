package com.juanasoapp.jobsityserieschallenge

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.juanasoapp.jobsityserieschallenge.serieslist.idlingResource
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Test
import kotlin.concurrent.thread

class SeriesListsFeatures :BaseUITest(){

    @Test
    fun displayAppTitle(){
        assertDisplayed(R.string.app_display_title)
    }

    @Test
    fun displaySearchBar(){
        assertDisplayed(R.id.series_list_searchview)
    }

    @Test
    fun emptySeriesListOnNewSearch(){
        enterTextAndSearch()
        IdlingRegistry.getInstance().unregister(idlingResource)
        enterTextAndSearch("dexter")
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
            .check(matches(withText("Under the Dome")))
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
            .check(matches(withText("Under the Dome")))
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

    fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Change view text"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

    private fun enterTextAndSearch(textToSearch:String="Under the Dome" ) {
        onView(withId(R.id.series_list_searchview)).perform(click());
        onView(withId(R.id.series_list_searchview))
            .perform(SearchViewActionExtension.typeText(textToSearch))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
    }
}