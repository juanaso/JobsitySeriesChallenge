package com.juanasoapp.jobsityserieschallenge.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.juanasoapp.jobsityserieschallenge.MainActivity
import com.juanasoapp.jobsityserieschallenge.R
import com.juanasoapp.jobsityserieschallenge.core.idlingResource
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {

    val seresTitleDummy = "Under the Dome"
    val textToSearchDummy = "dexter"
    val seriesDetailSeasonDummy = "Season 1"
    val seriesDetailEpisodeTitle = "1 - Pilot"


    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(idlingResource)
    }
    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }

    fun withChildViewCount(count: Int, childMatcher: Matcher<View>? = null): Matcher<View> {
        return object : BoundedMatcher<View, ViewGroup>(ViewGroup::class.java) {
            override fun matchesSafely(viewGroup: ViewGroup): Boolean {
                val matchCount = viewGroup.children
                    .filter { childMatcher?.matches(it)?:true }
                    .count()
                return matchCount == count
            }

            override fun describeTo(description: Description) {
                description.appendText("with child count $count")
            }
        }
    }



    fun NavigateToDetail() {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.series_name),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.series_list), 0))
            )
        ).perform(ViewActions.click())
    }

}