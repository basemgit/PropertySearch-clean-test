package basem.com.propertysearch


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import basem.com.propertysearch.MyCustomViewMatchers.withDrawable
import basem.com.propertysearch.common.utils.EspressoIdlingResource
import basem.com.propertysearch.screens.ui.activities.MainActivity
import basem.com.propertysearch.screens.ui.fragments.PropertyDetailsFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PropertyDetailsFragmentUITest {

    private val testFragment = PropertyDetailsFragment()

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java,
            true, true)

    @Before
    fun init() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        activityRule.activity.supportFragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, testFragment, "TEST")
                .commit()
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testPropertyDetails() {
        onView(withId(R.id.photo)).check(matches(withDrawable(R.drawable.office)));

    }

}







