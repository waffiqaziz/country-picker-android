package com.waffiq.countryPicker

import com.waffiq.countryPicker.countryPicker.R.drawable.flag_jp
import com.waffiq.countryPicker.countryPicker.model.Country
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@LooperMode(LooperMode.Mode.PAUSED) // To pause the main looper for testing
class MainActivityTest {

  private lateinit var activity: MainActivity

  @Before
  fun setUp() {
    // Initialize the activity
    activity = Robolectric.buildActivity(MainActivity::class.java).create().start().resume().get()
    // Process any queued tasks in the main looper
    shadowOf(activity.mainLooper).idle()
  }
  @Test
  fun testDefaultCountry() {
    // Idle the looper to process any pending tasks
    shadowOf(activity.mainLooper).idle()

    // Assert that the default country is set correctly
    val defaultCountry = "ID"
    assert(activity.binding.cpb.selectedCountryCode.isoCode == defaultCountry){
      "Expected country code '$defaultCountry' but was '${activity.binding.cpb.selectedCountryCode.isoCode}'"
    }
  }

  @Test
  fun testCountrySelection() {
    // Simulate selecting a country
    activity.binding.cpb.onCountrySelectedListener?.invoke(
      Country(
        "Japan",
        "+81",
        "JP",
        flag_jp
      )
    )

    // Idle the looper to process any pending tasks
    shadowOf(activity.mainLooper).idle()

    // Check the last displayed Toast message
    val lastToast = ShadowToast.getTextOfLatestToast()
    assert(lastToast == "Japan") { "Expected 'Japan' but got '$lastToast'" }
  }
}