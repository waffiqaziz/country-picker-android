package com.waffiq.countryPicker

import androidx.recyclerview.widget.DiffUtil
import com.waffiq.countryPicker.model.Country

class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
  override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
    // Check if the items represent the same country (using isoCode, for instance)
    return oldItem.isoCode == newItem.isoCode
  }

  override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
    // Check if the contents are the same
    return oldItem.isoCode == newItem.isoCode
  }
}
