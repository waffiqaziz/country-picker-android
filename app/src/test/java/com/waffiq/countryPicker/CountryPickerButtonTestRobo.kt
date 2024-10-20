package com.waffiq.countryPicker

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.waffiq.CountryPickerButton
import com.waffiq.countryPicker.R.id.cpb
import com.waffiq.countryPicker.countryPicker.CountryAdapter
import com.waffiq.countryPicker.countryPicker.R.id.rv_country
import com.waffiq.countryPicker.countryPicker.R.id.tv_country_id
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@LooperMode(LooperMode.Mode.PAUSED)
class CountryPickerButtonTestRobo {
  private lateinit var context: Context
  private lateinit var activity: TestActivity
  private lateinit var countryPickerButton: CountryPickerButton

  @Before
  @Throws(Exception::class)
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    activity = Robolectric.buildActivity(TestActivity::class.java)
      .create()
      .start()
      .resume()
      .get()

    countryPickerButton = activity.findViewById(cpb)
  }

  @Test
  fun testCountryPickerDialogOpens() {

    // Click the CountryPickerButton
    countryPickerButton.performClick()
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Check if the RecyclerView with country list is displayed
    val dialog = countryPickerButton.countryPickerDialog
    assertNotNull(dialog)
    val recyclerView: RecyclerView = dialog.findViewById(rv_country)
    assertNotNull(recyclerView)
  }

  @Test
  fun testCountrySelectionUpdatesButton() {

    // Click the CountryPickerButton to open the dialog
    countryPickerButton.performClick()
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Select the first country in the list
    val dialog = countryPickerButton.countryPickerDialog
    assertNotNull(dialog)
    val recyclerView: RecyclerView = dialog.findViewById(rv_country)
    val countryViewHolder =
      recyclerView.findViewHolderForAdapterPosition(0) as CountryAdapter.CountryViewHolder
    assertNotNull(countryViewHolder)
    countryViewHolder.itemView.performClick()

    // get selected country
    val selectedIsoCountry = countryPickerButton.findViewById<TextView>(
      tv_country_id).text.toString()
    
    // Check if the button text is updated with selected country
    assertEquals(selectedIsoCountry, countryPickerButton.getCurrentCountry())
  }
}
