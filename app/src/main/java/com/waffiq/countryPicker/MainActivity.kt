package com.waffiq.countryPicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.waffiq.countryPicker.databinding.ActivityMainBinding
import com.waffiq.countryPicker.model.Helper.countryList

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val countryPickerDialog = CountryPickerDialog(this, countryList)
    countryPickerDialog.adapter.onItemClick = { country ->
      binding.btnOpenDialog.ivCountryFlag.setImageResource(getFlagResIdByIsoCode(country.isoCode))
      binding.btnOpenDialog.tvCountryName.text = country.isoCode
      countryPickerDialog.dismiss()
    }

    binding.btnOpenDialog.root.setOnClickListener { countryPickerDialog.show() }
  }

  fun getFlagResIdByIsoCode(isoCode: String): Int {
    return countryList.find { it.isoCode == isoCode }?.flagResId
      ?: throw IllegalArgumentException("Country with isoCode $isoCode not found")
  }
}