package com.waffiq.countryPicker

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.waffiq.countryPicker.countryPicker.R.id.rv_country
import com.waffiq.countryPicker.countryPicker.R.id.tv_country_id
import com.waffiq.countryPicker.utils.EspressoIdlingResource
import com.waffiq.countryPicker.R.id.cpb
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryPickerButtonInstrumentedTest {
  private lateinit var scenario: ActivityScenario<TestActivity>
  private val context: Context = ApplicationProvider.getApplicationContext()

  @get:Rule
  val activityRule = ActivityScenarioRule(TestActivity::class.java)

  @Before
  fun setUp() {
    IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
  }

  @After
  fun tearDown() {
    IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
  }

  @Test
  fun testCountryPickerDialogOpens() {
    scenario = launchActivity(Intent(context, TestActivity::class.java))

    // Click the CountryPickerButton
    onView(withId(cpb)).perform(click())

    // Check if the RecyclerView with country list is displayed
    onView(withId(rv_country)).check(matches(isDisplayed()))
  }

  @Test
  fun testCountrySelectionUpdatesButton() {
    scenario = launchActivity(Intent(context, TestActivity::class.java))

    // Click the CountryPickerButton to open the dialog
    onView(withId(cpb)).perform(click())

    // Select the first country in the list
    onView(withText("Andorra")).perform(click())

    // Check if the button text is updated with selected country
    onView(withId(tv_country_id)).check(matches(withText("AD")))
  }
}
