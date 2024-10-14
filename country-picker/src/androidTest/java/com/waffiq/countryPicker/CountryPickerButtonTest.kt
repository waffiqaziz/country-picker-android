package com.waffiq.countryPicker

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.test.core.app.ApplicationProvider
import com.waffiq.countryPicker.model.Country
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountryPickerButtonTest {
  private lateinit var countryPickerButton: CountryPickerButton

  @Before
  fun setup() {
    MockitoAnnotations.openMocks(this)
    // Use a ContextThemeWrapper with a MaterialComponents theme
    val themeWrapper = ContextThemeWrapper(
      ApplicationProvider.getApplicationContext(),
      R.style.Base_Theme_CountryPicker // Use your app's MaterialComponents-based theme
    )
    countryPickerButton = CountryPickerButton(themeWrapper, null) // No AttributeSet needed for now
  }

  @Test
  fun testDefaultCountryInitialization() {
    val defaultCountry = countryPickerButton.getCurrentCountry()
    assertEquals("ID", defaultCountry) // Assuming "ID" is the default country.
  }

  @Test
  fun testSetSelectedCountry() {
    val selectedCountry = Country("India", "+91", "IN", R.drawable.flag_in)
    countryPickerButton.setCountry(selectedCountry.isoCode)
    assertEquals("IN", countryPickerButton.getCurrentCountry())
  }

  @Test
  fun testInvalidCountrySelection() {
    try {
      countryPickerButton.setCountry("true") // Invalid country code
      fail("Expected IllegalArgumentException")
    } catch (e: IllegalArgumentException) {
      assertEquals("Country not supported", e.message)
    }
  }
}
