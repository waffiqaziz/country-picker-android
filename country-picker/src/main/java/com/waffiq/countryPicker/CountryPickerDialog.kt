package com.waffiq.countryPicker

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.waffiq.countryPicker.databinding.DialogCountryPickerBinding
import com.waffiq.countryPicker.model.Helper.countryList

class CountryPickerDialog(
  context: Context,
  private val isAutoFocus: Boolean,
  private val dialogTextColor: Int,
  private val dialogTextHintColor: Int,
  private val dialogBackgroundColor: Int,
  private val dialogSearchBackgroundColor: Int,
  private val applyBorderSearch: Boolean,
  private val dialogSearchBorderColor: Int,
  private val iconSearchColor: Int,
  private val typeface: Typeface?,
) : Dialog(context) {
  val adapter = CountryAdapter(dialogTextColor,typeface)

  private lateinit var binding: DialogCountryPickerBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DialogCountryPickerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupDialog()
    setupRecyclerView()
    searchCountry()
    applyAutoFocus()
    applyTextHintColor()
    applyDialogTextColor()
    applyDialogBackgroundColor()
    applyDialogSearchBackgroundColor()
    applyBorderSearch()
    applyIconSearchColor()
    applyTypeFace()
  }

  private fun setupDialog() {
    // Set fixed width in dp
    val widthInPx = dpToPx(320)
    window?.setLayout(widthInPx, ViewGroup.LayoutParams.WRAP_CONTENT)

    val insetDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_dialog_background) as? InsetDrawable
    val shapeDrawable = insetDrawable?.drawable as? GradientDrawable
    shapeDrawable?.setColor(dialogBackgroundColor)
    window?.setBackgroundDrawable(insetDrawable)

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

  private fun setupRecyclerView() {

//    val divider = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//    divider.isLastItemDecorated = false
//    binding.rvCountry.addItemDecoration(divider)

    adapter.submitList(countryList)
    binding.rvCountry.adapter = adapter
    binding.rvCountry.layoutManager = LinearLayoutManager(context)
    binding.btnClear.setOnClickListener {
      Log.e("KKKKKKKKKKKKKKKK", binding.etSearch.text.toString())
      if (binding.etSearch.text.isNotEmpty()) {
        binding.etSearch.text.clear()
      } else  {
        binding.etSearch.clearFocus()
        dismiss()
      }
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

  private fun applyAutoFocus(){
    if(isAutoFocus) binding.etSearch.requestFocus()
  }

  private fun applyDialogTextColor() {
    binding.etSearch.setTextColor(dialogTextColor)
    binding.tvHeaderSearch.setTextColor(dialogTextColor)
  }

  private fun applyDialogBackgroundColor() {
    val backgroundDrawable = window?.decorView?.background as? GradientDrawable
    backgroundDrawable?.setColor(dialogBackgroundColor)  // Set a new color (e.g., orange)
  }

  private fun applyDialogSearchBackgroundColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      binding.etSearch.background.colorFilter =
        BlendModeColorFilter(dialogSearchBackgroundColor, BlendMode.SRC_ATOP)
    } else {
      @Suppress("DEPRECATION")
      binding.etSearch.background.setColorFilter(
        dialogSearchBackgroundColor,
        PorterDuff.Mode.SRC_ATOP
      )
    }
  }

  private fun applyIconSearchColor() {
    binding.btnClear.imageTintList = ColorStateList.valueOf(iconSearchColor)
  }

  private fun applyTextHintColor(){
    binding.etSearch.setHintTextColor(dialogTextHintColor)
  }

  private fun applyBorderSearch() {
    if (applyBorderSearch) {
      val shape = GradientDrawable()
      shape.shape = GradientDrawable.RECTANGLE
      shape.cornerRadius = 16f
      shape.setStroke(2, dialogSearchBorderColor)
      shape.setColor(dialogSearchBackgroundColor)
      binding.etSearch.background = shape
    }
  }

  private fun applyTypeFace(){
    binding.etSearch.typeface = typeface
    binding.tvHeaderSearch.typeface = typeface
  }
}