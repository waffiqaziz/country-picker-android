package com.waffiq.countryPicker.model

data class Country(
  val name: String,
  val phoneCode: String,
  val isoCode: String, // ISO code of the country
  val flagResId: Int   // Resource ID of the country's flag drawable
)