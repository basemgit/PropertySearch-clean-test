package basem.com.propertysearch
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import basem.com.propertysearch.common.utils.EspressoIdlingResource
import basem.com.propertysearch.screens.ui.activities.MainActivity
import basem.com.propertysearch.screens.ui.fragments.SearchResultsFragment
import com.google.common.base.Preconditions.checkArgument
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchResultsFragmentUITest {

    private val searchFragment = SearchResultsFragment()


    private fun withItemText(itemText: String): Matcher<View> {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty")
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView::class.java)),
                        withText(itemText)).matches(item)
            }

            override fun describeTo(description: Description) {
                description.appendText("is isDescendantOf A Recycler View with text $itemText")
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
        activityRule.activity.supportFragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, searchFragment, "TEST")
                .commit()
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testSearchResultsList()
    {
        val action = RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4)

        onView(withId(R.id.recyclerView)).perform(action)

        onView(withItemText("580000")).check(matches(isDisplayed()))

    }

    }







