package com.waffiq.countryPicker

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.waffiq.countryPicker.R.id.cpb
import com.waffiq.countryPicker.countryPicker.R.id.btn_clear
import com.waffiq.countryPicker.countryPicker.R.id.et_search
import com.waffiq.countryPicker.countryPicker.R.id.rv_country
import com.waffiq.countryPicker.countryPicker.R.id.tv_country_name
import com.waffiq.countryPicker.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class CountryPickerDialogTestJUnit {
  private lateinit var scenario: ActivityScenario<MainActivity>
  private val context: Context = ApplicationProvider.getApplicationContext()

  @get:Rule
  val activityRule = ActivityScenarioRule(MainActivity::class.java)

  @Before
  fun setUp() {
    IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
  }

  @After
  fun tearDown() {
    IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
  }

  @Test
  fun testSearchCountryFilter() {
    scenario = launchActivity(Intent(context, MainActivity::class.java))
    // Open dialog
    onView(withId(cpb)).perform(click())

    // Search for a country
    onView(withId(et_search)).perform(typeText("Indonesia"))

    // Check if the correct country is shown
    onView(withId(tv_country_name)).check(matches(withText("Indonesia")))
  }

  @Test
  fun testSelectCountryViaScrolling() {
    scenario = launchActivity(Intent(context, MainActivity::class.java))

    // Click the CountryPickerButton to open the dialog
    onView(withId(cpb)).perform(click())

    onView(withId(rv_country)).perform(
      RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
        115
      )
    )

    // Select the first country in the list
    onView(withText("Indonesia")).perform(click())
  }

  @Test
  fun testClearSearchField() {
    scenario = launchActivity(Intent(context, MainActivity::class.java))
    // Open dialog
    onView(withId(cpb)).perform(click())

    // Enter some text in search field
    onView(withId(et_search)).perform(typeText("Indonesia"))

    // Clear the search field using the clear button
    onView(withId(btn_clear)).perform(click())

    // Check if search field is empty
    onView(withId(et_search)).check(matches(withText("")))
  }
}