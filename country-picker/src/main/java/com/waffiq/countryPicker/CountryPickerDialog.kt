package com.waffiq.countryPicker

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.waffiq.countryPicker.databinding.DialogCountryPickerBinding
import com.waffiq.countryPicker.model.Country

class CountryPickerDialog(
  context: Context,
  private val countryList: List<Country>
) : Dialog(context) {

  val adapter = CountryAdapter()

  private lateinit var binding: DialogCountryPickerBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DialogCountryPickerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupDialog()
    setupRecyclerView()
    searchCountry()
  }

  private fun setupDialog() {
    // Set fixed width in dp
    val widthInPx = dpToPx(320)
    window?.setLayout(widthInPx, ViewGroup.LayoutParams.WRAP_CONTENT)
    window?.setBackgroundDrawableResource(R.drawable.rounded_dialog_background)

    // Set soft input mode to pan the dialog when keyboard appears
    // keeps the current size of the dialog and moves it upwards when the keyboard appears
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      ViewCompat.setOnApplyWindowInsetsListener(window?.decorView!!) { _, insets ->
        val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        val navigationBarHeight =
          insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
        binding.root.setPadding(0, 0, 0, imeHeight - navigationBarHeight)
        insets
      }
    } else {
      @Suppress("DEPRECATION")
      window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }


  }

  private fun dpToPx(dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun setupRecyclerView() {

//    val divider = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//    divider.isLastItemDecorated = false
//    binding.rvCountry.addItemDecoration(divider)

    adapter.submitList(countryList)
    binding.rvCountry.adapter = adapter
    binding.rvCountry.layoutManager = LinearLayoutManager(context)

    binding.etSearch.setOnTouchListener { v, event ->
      if (event.action == MotionEvent.ACTION_UP) {
        if (event.rawX >= (binding.etSearch.right - binding.etSearch.compoundDrawables[2].bounds.width())) {
          binding.etSearch.text?.clear()
          binding.etSearch.performClick()
          return@setOnTouchListener true
        }
      }
      false
    }
  }

  private fun searchCountry() {
    // Filter countries by name or phone code
    binding.etSearch.addTextChangedListener {
      val query = it.toString().lowercase()
      val filteredList = countryList.filter { country ->
        country.name.lowercase().startsWith(query, ignoreCase = true) || // Search by name
          country.phoneCode.drop(1).startsWith(query, ignoreCase = true) || // Search by phone code
          country.isoCode.lowercase().contains(query) // Search by ISO code
      }
      adapter.submitList(filteredList)
      binding.rvCountry.adapter = adapter
    }
  }
}