package com.waffiq.countryPicker

import android.view.ContextThemeWrapper
import androidx.test.core.app.ApplicationProvider
import com.waffiq.CountryPickerButton
import com.waffiq.countryPicker.countryPicker.R.drawable.flag_in
import com.waffiq.countryPicker.countryPicker.R.style.Base_Theme_CountryPicker
import com.waffiq.countryPicker.countryPicker.model.Country
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class CountryPickerButtonTest {
  private lateinit var countryPickerButton: CountryPickerButton

  @Before
  fun setup() {
    MockitoAnnotations.openMocks(this)
    // Use a ContextThemeWrapper with  MaterialComponents theme
    val themeWrapper = ContextThemeWrapper(
      ApplicationProvider.getApplicationContext(),
      Base_Theme_CountryPicker
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
    val selectedCountry = Country("India", "+91", "IN", flag_in)
    countryPickerButton.setCountry(selectedCountry.isoCode)
    assertEquals("IN", countryPickerButton.getCurrentCountry())
  }

  @Test
  fun testInvalidCountrySelection() {
    // Act: Call setCountry with an invalid country code
    val result = countryPickerButton.setCountry("true") // Invalid country code

    // Assert: Check that the result is a failure
    assertTrue(result.isFailure)

    // Assert: Check that the exception is of type IllegalArgumentException
    val exception = result.exceptionOrNull()
    assertNotNull(exception)
    assertTrue(exception is IllegalArgumentException)

    // Assert: Check that the exception message is correct
    assertEquals("Country not supported", exception?.message)
  }
}
