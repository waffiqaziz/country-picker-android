package com.waffiq.countryPicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.card.MaterialCardView
import com.waffiq.countryPicker.databinding.DialogViewBinding
import com.waffiq.countryPicker.model.Country
import com.waffiq.countryPicker.model.Helper.countryList

class CountryPickerButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
  private lateinit var binding: DialogViewBinding
  private lateinit var countryPickerDialog: CountryPickerDialog

  // Define a listener for the selected country
  var onCountrySelectedListener: ((Country) -> Unit)? = null

  init {
    setupView(context)
    initAttributes(context, attrs)
    setupClickListener()
  }

  @SuppressLint("SetTextI18n")
  private fun setupView(context: Context) {
    binding = DialogViewBinding.inflate(LayoutInflater.from(context), this, true)

    // Set default values
    binding.ivCountryFlag.setImageResource(R.drawable.flag_id)
    binding.tvCountryName.text = "ID"
  }

  private fun setupClickListener() {
    setOnClickListener {
      countryPickerDialog.adapter.onItemClick = { country ->
        // Update the country flag and name on selection
        binding.ivCountryFlag.setImageResource(getFlagResIdByIsoCode(country.isoCode))
        binding.tvCountryName.text = country.isoCode

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
  private fun getFlagResIdByIsoCode(isoCode: String): Int {
    return countryList.find { it.isoCode == isoCode }?.flagResId
      ?: throw IllegalArgumentException("Country with isoCode $isoCode not found")
  }

  private fun initAttributes(context: Context, attrs: AttributeSet?) {
    if (attrs != null) {
      // change attribute flag
      val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountryPickerButton)
      val defaultCountryIso =
        typedArray.getString(R.styleable.CountryPickerButton_cpa_defaultCountryISO)
      if (defaultCountryIso != null) setCountry(defaultCountryIso)


      // get all attribute
      val isAutoFocus = typedArray.getBoolean(
        R.styleable.CountryPickerButton_cpa_dialogAutoFocus,
        false
      )
      val dialogBackgroundColor =
        typedArray.getColor(
          R.styleable.CountryPickerButton_cpa_dialogBackgroundColor,
          0xFFF5F5DC.toInt()
        )
      val dialogSearchBackgroundColor =
        typedArray.getColor(
          R.styleable.CountryPickerButton_cpa_dialogSearchBackgroundColor,
          0xFFE6E6CA.toInt()
        )
      val dialogTextColor =
        typedArray.getColor(R.styleable.CountryPickerButton_cpa_dialogTextColor, Color.BLACK)
      val dialogTexHintColor =
        typedArray.getColor(R.styleable.CountryPickerButton_cpa_dialogTextHintColor, Color.BLACK)
      val applyBorder =
        typedArray.getBoolean(R.styleable.CountryPickerButton_cpa_dialogApplyBorderSearch, false)
      val borderColor =
        typedArray.getColor(R.styleable.CountryPickerButton_cpa_dialogSearchBorderColor, Color.GRAY)
      val iconSearchColor =
        typedArray.getColor(
          R.styleable.CountryPickerButton_cpa_dialogSearchCrossIconColor,
          Color.BLACK
        )

      // font family
      var fontFamily: Typeface? = null
      val fontResId =
        typedArray.getResourceId(R.styleable.CountryPickerButton_cpa_fontFamily, -1)
      if (fontResId != -1) fontFamily = ResourcesCompat.getFont(context, fontResId)
      binding.tvCountryName.typeface = fontFamily

      typedArray.recycle()

      binding.tvCountryName.setTextColor(dialogTextColor)
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

  fun setCountry(countryName: String) {
    val defaultCountry = countryList.find { it.isoCode == countryName }
    if (defaultCountry != null) {
      binding.ivCountryFlag.setImageResource(defaultCountry.flagResId)
      binding.tvCountryName.text = defaultCountry.isoCode
    } else {
      throw IllegalArgumentException("Country not supported")
    }
  }

  fun getCurrentCountry(): String {
    return binding.tvCountryName.text.toString()
  }
}

