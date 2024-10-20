package com.waffiq.countryPicker

import android.content.Context
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.waffiq.CountryPickerButton
import com.waffiq.countryPicker.R.id.cpb
import com.waffiq.countryPicker.countryPicker.CountryAdapter
import com.waffiq.countryPicker.countryPicker.R.id.et_search
import com.waffiq.countryPicker.countryPicker.R.id.rv_country
import com.waffiq.countryPicker.countryPicker.R.id.btn_clear
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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
class CountryPickerDialogTestRobolectric {

  private lateinit var context: Context
  private lateinit var activity: MainActivity
  private lateinit var countryPickerButton: CountryPickerButton

  @Before
  @Throws(Exception::class)
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    activity = Robolectric.buildActivity(MainActivity::class.java)
      .create()
      .start()
      .resume()
      .get()

    countryPickerButton = activity.findViewById(cpb)
  }

  @Test
  fun testSearchCountryFilter() {
    // Step 1: Simulate click to open the CountryPickerDialog
    countryPickerButton.performClick()

    // Step 2: Run UI thread tasks to ensure dialog appears
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Step 3: Access the dialog directly
    val dialog =
      countryPickerButton.countryPickerDialog // This assumes you have exposed this property

    // Ensure dialog is not null
    assertNotNull(dialog)

    // Step 4: Access the dialog views directly
    val searchEditText: EditText = dialog.findViewById(et_search)
    val countryRecyclerView: RecyclerView = dialog.findViewById(rv_country)

    // Step 5: Input search text "Indonesia"
    searchEditText.setText(NAME_INDONESIA)
    // Trigger the text change event manually
    searchEditText.text.clear()
    searchEditText.append(NAME_INDONESIA)
    searchEditText.dispatchTouchEvent(
      MotionEvent.obtain(
        System.currentTimeMillis(),
        System.currentTimeMillis(),
        MotionEvent.ACTION_DOWN,
        0f,
        0f,
        0
      )
    )
    searchEditText.dispatchTouchEvent(
      MotionEvent.obtain(
        System.currentTimeMillis(),
        System.currentTimeMillis(),
        MotionEvent.ACTION_UP,
        0f,
        0f,
        0
      )
    )

    // Step 6: Run the UI thread to ensure everything processes
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Step 7: Check that "Indonesia" is present in the adapter
    val adapter = countryRecyclerView.adapter as CountryAdapter
    assertTrue(adapter.currentList.any { it.name.equals(NAME_INDONESIA, ignoreCase = true) })
  }

  @Test
  fun testSelectCountryViaScrolling() {
    // Step 1: Simulate click to open the CountryPickerDialog
    val countryPickerButton: CountryPickerButton = activity.findViewById(cpb)
    countryPickerButton.performClick()

    // Step 2: Run UI thread tasks to ensure the dialog appears
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Step 3: Access the CountryPickerDialog directly
    val dialog = countryPickerButton.countryPickerDialog
    assertNotNull(dialog)

    // Step 4: Access the RecyclerView in the dialog
    val recyclerView: RecyclerView = dialog.findViewById(rv_country)

    // Step 5: Scroll to the desired position
    recyclerView.scrollToPosition(INDEX_INDONESIA)

    // Step 6: Perform click on the "Indonesia" item
    val adapter = recyclerView.adapter as CountryAdapter
    assertNotNull(adapter) // Make sure the adapter is set

    //    adapter.submitList(countryList)
    //    dialog.adapter.notifyDataSetChanged()

    // Run UI thread tasks to ensure get
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    val countryViewHolder =
      recyclerView.findViewHolderForAdapterPosition(INDEX_INDONESIA) as CountryAdapter.CountryViewHolder

    // Check that the view holder is not null and the country name matches
    assertNotNull(countryViewHolder)
    assertEquals(NAME_INDONESIA, adapter.currentList[INDEX_INDONESIA].name)

    // Step 7: Perform click on the ViewHolder item
    countryViewHolder.itemView.performClick()

    // Step 8: Verify that the selection was made
    val selectedIsoCountry =
      countryPickerButton.getCurrentCountry() // Adjust this according to your implementation
    assertEquals(ISO_INDONESIA, selectedIsoCountry)
  }

  @Test
  fun testClearSearchField() {
    // Step 1: Simulate click to open the CountryPickerDialog
    val countryPickerButton: CountryPickerButton = activity.findViewById(cpb)
    countryPickerButton.performClick()

    // Step 2: Run UI thread tasks to ensure the dialog appears
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Step 3: Access the CountryPickerDialog directly
    val dialog = countryPickerButton.countryPickerDialog
    assertNotNull(dialog)

    // Step 4: Access the EditText Search in the dialog
    val searchEditText: EditText = dialog.findViewById(et_search)
    searchEditText.setText(NAME_INDONESIA)

    // Step 5: Run UI thread tasks again after typing text
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Step 6: Find the clear button and perform a click
    val clearButton = dialog.findViewById<ImageButton>(btn_clear)
    clearButton.performClick()

    // Step 7: Check that the search field is now empty
    assertEquals("", searchEditText.text.toString())
  }

  companion object {
    const val INDEX_INDONESIA = 104
    const val NAME_INDONESIA = "Indonesia"
    const val ISO_INDONESIA = "ID"
  }
}
