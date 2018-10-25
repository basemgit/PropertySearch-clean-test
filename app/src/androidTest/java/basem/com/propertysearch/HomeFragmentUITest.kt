package basem.com.propertysearch

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Checks
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import basem.com.propertysearch.common.utils.EspressoIdlingResource
import basem.com.propertysearch.screens.ui.activities.MainActivity
import basem.com.propertysearch.screens.ui.fragments.HomeFragment
import com.google.common.base.Verify.verify
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeFragmentUITest {


    private fun hasBackgroundColor(colorRes: Int): Matcher<View> {
        Checks.checkNotNull(colorRes)

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("background color: $colorRes")
            }

            override fun matchesSafely(item: View?): Boolean {

                val context = item?.context
                val currentColor = (item?.background as ColorDrawable).color
                val expectedColor: Int?

                expectedColor = if (Build.VERSION.SDK_INT <= 22) {
                    context?.resources?.getColor(colorRes)
                } else {
                    context?.getColor(colorRes)
                }

                return currentColor == expectedColor
            }
        }
    }


    fun withItemContent(itemTextMatcher: Matcher<String>): Matcher<Any> {
        checkNotNull(itemTextMatcher)
        return object : BoundedMatcher<Any, String>(String::class.java) {
            public override fun matchesSafely(item: String): Boolean {
                return itemTextMatcher.matches(item)
            }

            override fun describeTo(description: Description) {
                description.appendText("with bankName: $description")
            }
        }
    }


    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java,
            true, true)

    @Before
    fun init() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testForSaleBtn() {
        onView(withId(R.id.forSaleBtn)).perform(click())
        onView(withId(R.id.forSaleBtn)).check(matches(hasBackgroundColor(R.color.colorPrimary)))
        onView(withId(R.id.forRentBtn)).check(matches(hasBackgroundColor(R.color.colorBackground)))
    }


    @Test
    fun testForRentBtn() {
        onView(withId(R.id.forRentBtn)).perform(click())
        onView(withId(R.id.forRentBtn)).check(matches(hasBackgroundColor(R.color.colorPrimary)))
        onView(withId(R.id.forSaleBtn)).check(matches(hasBackgroundColor(R.color.colorBackground)))
    }

    @Test
    fun testCitySpinner() {
        onView(withId(R.id.spinnerCity)).check(matches(isDisplayed()))
        onView(withId(R.id.spinnerCity)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Greater Cairo"))).check(matches(isDisplayed()))
    }

    @Test
    fun testAreaSpinner() {
        onView(withId(R.id.spinnerArea)).check(matches(isDisplayed()))
        onView(withId(R.id.spinnerArea)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("New Cairo - Tagamoa"))).check(matches(isDisplayed()))
    }

    @Test
    fun testTypeSpinner() {
        onView(withId(R.id.spinnerType)).check(matches(isDisplayed()))
        onView(withId(R.id.spinnerType)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Apartment"))).check(matches(isDisplayed()))
    }

    @Test
    fun testMaxPriceSpinner() {
        onView(withId(R.id.spinnerMaxPrice)).check(matches(isDisplayed()))
        onView(withId(R.id.spinnerMaxPrice)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("100000"))).check(matches(isDisplayed()))
    }


    @Test
    fun testMinPriceSpinner() {
        onView(withId(R.id.spinnerMinPrice)).check(matches(isDisplayed()))
        onView(withId(R.id.spinnerMinPrice)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("100000"))).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchBtn() {
        onView(withId(R.id.searchBtn)).perform(click())
        verify(HomeFragment.isValidSearchCriteria())
    }


}
