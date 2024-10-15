package com.waffiq.countryPicker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.waffiq.countryPicker.R.id.cpb
import com.waffiq.countryPicker.countryPicker.R.id.btn_clear
import com.waffiq.countryPicker.countryPicker.R.id.et_search
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryPickerDialogTest {

  @Test
  fun testSearchCountryFilter() {
    // Open dialog
    onView(withId(cpb)).perform(click())

    // Search for a country
    onView(withId(et_search)).perform(typeText("India"))

    // Check if the correct country is shown
    onView(withText("India")).check(matches(isDisplayed()))
  }

  @Test
  fun testClearSearchField() {
    // Open dialog
    onView(withId(cpb)).perform(click())

    // Enter some text in search field
    onView(withId(et_search)).perform(typeText("Canada"))

    // Clear the search field using the clear button
    onView(withId(btn_clear)).perform(click())

    // Check if search field is empty
    onView(withId(et_search)).check(matches(withText("")))
  }
}