package com.waffiq.countryPicker

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.waffiq.CountryPickerButton
import com.waffiq.countryPicker.R.id.cpb_test
import com.waffiq.countryPicker.countryPicker.CountryAdapter
import com.waffiq.countryPicker.countryPicker.R.id.btn_clear
import com.waffiq.countryPicker.countryPicker.R.id.et_search
import com.waffiq.countryPicker.countryPicker.R.id.rv_country
import com.waffiq.countryPicker.countryPicker.R.id.tv_country_name
import com.waffiq.countryPicker.countryPicker.R.id.tv_country_code
import com.waffiq.countryPicker.countryPicker.R.id.tv_header_search
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
class CountryPickerDialogTestRobo {

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

    countryPickerButton = activity.findViewById(cpb_test)
  }

  @Test
  fun testSearchCountryFilter() {
    // Simulate click to open the CountryPickerDialog
    countryPickerButton.performClick()

    // Run UI thread tasks to ensure dialog appears
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Access the dialog directly
    val dialog =
      countryPickerButton.countryPickerDialog // This assumes you have exposed this property

    // Ensure dialog is not null
    assertNotNull(dialog)

    // Access the dialog views directly
    val searchEditText: EditText = dialog.findViewById(et_search)
    val countryRecyclerView: RecyclerView = dialog.findViewById(rv_country)

    // Input search text "Indonesia"
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

    // Run the UI thread to ensure everything processes
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Check that "Indonesia" is present in the adapter
    val adapter = countryRecyclerView.adapter as CountryAdapter
    assertTrue(adapter.currentList.any { it.name.equals(NAME_INDONESIA, ignoreCase = true) })
  }

  @Test
  fun testSelectCountryViaScrolling() {
    // Simulate click to open the CountryPickerDialog
    countryPickerButton.performClick()

    // Run UI thread tasks to ensure the dialog appears
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Access the CountryPickerDialog directly
    val dialog = countryPickerButton.countryPickerDialog
    assertNotNull(dialog)

    // Access the RecyclerView in the dialog
    val recyclerView: RecyclerView = dialog.findViewById(rv_country)

    // Scroll to the desired position
    recyclerView.scrollToPosition(INDEX_INDONESIA)

    // Perform click on the "Indonesia" item
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

    // Perform click on the ViewHolder item
    countryViewHolder.itemView.performClick()

    // Verify that the selection was made
    val selectedIsoCountry =
      countryPickerButton.getCurrentCountry() // Adjust this according to your implementation
    assertEquals(ISO_INDONESIA, selectedIsoCountry)
  }

  @Test
  fun testClearSearchField() {
    // Simulate click to open the CountryPickerDialog
    countryPickerButton.performClick()

    // Run UI thread tasks to ensure the dialog appears
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Access the CountryPickerDialog directly
    val dialog = countryPickerButton.countryPickerDialog
    assertNotNull(dialog)

    // Access the EditText Search in the dialog
    val searchEditText: EditText = dialog.findViewById(et_search)
    searchEditText.setText(NAME_INDONESIA)

    // Run UI thread tasks again after typing text
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    // Find the clear button and perform a click
    val clearButton = dialog.findViewById<ImageButton>(btn_clear)
    clearButton.performClick()

    // Check that the search field is now empty
    assertEquals("", searchEditText.text.toString())
  }

  @Test
  fun testDialogTextColorApplied() {
    countryPickerButton.performClick()
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    val dialog = countryPickerButton.countryPickerDialog
    assertNotNull(dialog)

    val recyclerView: RecyclerView = dialog.findViewById(rv_country)
    recyclerView.scrollToPosition(INDEX_INDONESIA)

    val countryAdapter = recyclerView.adapter as CountryAdapter
    assertNotNull(countryAdapter)
    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

    val countryViewHolder =
      recyclerView.findViewHolderForAdapterPosition(INDEX_INDONESIA) as CountryAdapter.CountryViewHolder
    assertNotNull(countryViewHolder)

    // test text color inside item
    countryViewHolder.let {
      val tvCountryName = it.itemView.findViewById<TextView>(tv_country_name)
      val tvColorCountryName = tvCountryName.currentTextColor
      assertEquals(ContextCompat.getColor(context, R.color.dialogTextColor), tvColorCountryName)
      assertEquals(
        ContextCompat.getColor(context, R.color.dialogTextColor),
        it.itemView.findViewById<TextView>(tv_country_code).currentTextColor
      )
    }

    // test text hint color
    val etSearch: EditText = dialog.findViewById(et_search)
    val colorTextHint = etSearch.currentHintTextColor
    assertEquals(ContextCompat.getColor(context, R.color.dialogTextHintColor), colorTextHint)

    // test text header color
    val tvHeader: TextView = dialog.findViewById(tv_header_search)
    val colorTextHeader = tvHeader.currentTextColor
    assertEquals(ContextCompat.getColor(context, R.color.dialogTextColor), colorTextHeader)

    // test icon color
    val imageCross: ImageButton = dialog.findViewById(btn_clear)
    val colorImage = imageCross.imageTintList
    assertEquals(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dialogSearchCrossIconColor)), colorImage)
  }

//  @Test
//  fun testDialogBackgroundColorApplied() {
//    countryPickerButton.performClick()
//    ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
//
//    val dialog = countryPickerButton.countryPickerDialog
//    assertNotNull(dialog)
//    val etSearch: EditText = dialog.findViewById(et_search)
//
//    // Create expected color
//    val expectedColor = ContextCompat.getColor(context, R.color.darkPurple)
//    val backgroundColorTextSearch = etSearch.background.colorFilter
//
//    // Different assertions depending on API level
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//      assertNotNull(backgroundColorTextSearch)
//
//      assertTrue(backgroundColorTextSearch is BlendModeColorFilter)
//      assertTrue(backgroundColorTextSearch.toString().contains("SRC_ATOP"))
//      assertTrue(backgroundColorTextSearch.toString().contains(expectedColor.toString()))
//    } else {
//      // Check for PorterDuffColorFilter on older versions
//      assertTrue(backgroundColorTextSearch is PorterDuffColorFilter)
//    }
//  }

  companion object {
    const val INDEX_INDONESIA = 104
    const val NAME_INDONESIA = "Indonesia"
    const val ISO_INDONESIA = "ID"
  }
}
