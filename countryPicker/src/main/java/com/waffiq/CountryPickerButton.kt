package com.waffiq

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.card.MaterialCardView
import com.waffiq.countryPicker.countryPicker.CountryPickerDialog
import com.waffiq.countryPicker.countryPicker.R.drawable.flag_id
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_defaultCountryISO
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_dialogApplyBorderSearch
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_dialogAutoFocus
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_dialogBackgroundColor
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_dialogSearchBackgroundColor
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_dialogSearchBorderColor
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_dialogSearchCrossIconColor
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_dialogTextColor
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_dialogTextHintColor
import com.waffiq.countryPicker.countryPicker.R.styleable.CountryPickerButton_cpa_fontFamily
import com.waffiq.countryPicker.countryPicker.databinding.DialogViewBinding
import com.waffiq.countryPicker.countryPicker.model.Country
import com.waffiq.countryPicker.countryPicker.model.Helper.countryList

class CountryPickerButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
  private lateinit var binding: DialogViewBinding
  lateinit var countryPickerDialog: CountryPickerDialog

  // Define a listener for the selected country
  var onCountrySelectedListener: ((Country) -> Unit)? = null
  lateinit var selectedCountryCode: Country

  init {
    setupView(context)
    initAttributes(context, attrs)
    setupClickListener()
  }

  @SuppressLint("SetTextI18n")
  private fun setupView(context: Context) {
    binding = DialogViewBinding.inflate(LayoutInflater.from(context), this, true)

    // Set default values
    val defaultCountry = Country("Indonesia", "+62", "ID", flag_id)
    binding.ivCountryFlag.setImageResource(defaultCountry.flagResId)
    binding.tvCountryId.text = defaultCountry.isoCode
    selectedCountryCode = defaultCountry
  }

  private fun setupClickListener() {
    setOnClickListener {
      countryPickerDialog.adapter.onItemClick = { country ->
        selectedCountryCode = country

        // Update the country flag and name on selection
        setIdAndFlagByIsoCode(country.isoCode)

        // Notify the listener with the selected country
        onCountrySelectedListener?.invoke(country)
        Handler(Looper.getMainLooper()).postDelayed({
          countryPickerDialog.dismiss()
        }, 200)
      }

      // Show the country picker dialog
      countryPickerDialog.show()
    }
  }

  // Helper function to get the flag resource by ISO code
  private fun setIdAndFlagByIsoCode(isoCode: String) {
    val result = countryList.find { it.isoCode == isoCode }
    if (result != null) {
      binding.ivCountryFlag.setImageResource(result.flagResId)
      binding.tvCountryId.text = result.isoCode
      result.isoCode
    } else {
      Log.e("CountryPicker", "Country Not found, Make sure ISO Code is correct")
    }
  }

  private fun initAttributes(context: Context, attrs: AttributeSet?) {
    if (attrs != null) {
      // change attribute flag
      val typedArray = context.obtainStyledAttributes(attrs, CountryPickerButton)
      val defaultCountryIso =
        typedArray.getString(CountryPickerButton_cpa_defaultCountryISO)
      if (defaultCountryIso != null) setCountry(defaultCountryIso)


      // get all attribute
      val isAutoFocus = typedArray.getBoolean(
        CountryPickerButton_cpa_dialogAutoFocus,
        false
      )
      val dialogBackgroundColor =
        typedArray.getColor(
          CountryPickerButton_cpa_dialogBackgroundColor,
          0xFFF5F5DC.toInt()
        )
      val dialogSearchBackgroundColor =
        typedArray.getColor(
          CountryPickerButton_cpa_dialogSearchBackgroundColor,
          0xFFE6E6CA.toInt()
        )
      val dialogTextColor =
        typedArray.getColor(CountryPickerButton_cpa_dialogTextColor, Color.BLACK)
      val dialogTexHintColor =
        typedArray.getColor(CountryPickerButton_cpa_dialogTextHintColor, Color.BLACK)
      val applyBorder =
        typedArray.getBoolean(CountryPickerButton_cpa_dialogApplyBorderSearch, false)
      val borderColor =
        typedArray.getColor(CountryPickerButton_cpa_dialogSearchBorderColor, Color.GRAY)
      val iconSearchColor =
        typedArray.getColor(
          CountryPickerButton_cpa_dialogSearchCrossIconColor,
          Color.BLACK
        )

      // font family
      var fontFamily: Typeface? = null
      val fontResId =
        typedArray.getResourceId(CountryPickerButton_cpa_fontFamily, -1)
      if (fontResId != -1) fontFamily = ResourcesCompat.getFont(context, fontResId)
      binding.tvCountryId.typeface = fontFamily

      typedArray.recycle()

      binding.tvCountryId.setTextColor(dialogTextColor)
      countryPickerDialog = CountryPickerDialog(
        context,
        isAutoFocus,
        dialogTextColor,
        dialogTexHintColor,
        dialogBackgroundColor,
        dialogSearchBackgroundColor,
        applyBorder,
        borderColor,
        iconSearchColor,
        fontFamily,
      )
    }
  }

  fun setCountry(countryIsoCode: String): Result<Unit> {
    val defaultCountry = countryList.find { it.isoCode == countryIsoCode.uppercase() }
    return if (defaultCountry != null) {
      binding.ivCountryFlag.setImageResource(defaultCountry.flagResId)
      binding.tvCountryId.text = defaultCountry.isoCode
      Result.success(Unit)
    } else {
      Result.failure(IllegalArgumentException("Country not supported"))
    }
  }

  fun getCurrentCountry(): String {
    return binding.tvCountryId.text.toString()
  }
}

