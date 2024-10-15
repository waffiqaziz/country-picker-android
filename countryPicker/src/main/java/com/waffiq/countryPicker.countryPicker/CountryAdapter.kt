package com.waffiq.countryPicker.countryPicker

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waffiq.countryPicker.countryPicker.databinding.ItemCountryBinding
import com.waffiq.countryPicker.countryPicker.model.Country

class CountryAdapter(
  private var textColor: Int,
  private val typeface: Typeface?,
):
  ListAdapter<Country, CountryAdapter.CountryViewHolder>(CountryDiffCallback()) {

  var onItemClick: ((Country) -> Unit)? = null

  inner class CountryViewHolder(private var binding: ItemCountryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(country: Country) {
      binding.apply {
        countryFlag.contentDescription = country.name
        tvCountryName.text = country.name
        tvCountryCode.text = "(${country.isoCode})"
        countryFlag.setImageResource(country.flagResId)
        tvCountryPhone.text = country.phoneCode

        tvCountryName.post {
          val textWidth = tvCountryName.paint.measureText(tvCountryName.text.toString())
          val availableWidth = tvCountryName.width

          tvCountryCode.isVisible = textWidth <= availableWidth
        }

        // Set the text color
        tvCountryName.setTextColor(textColor)
        tvCountryCode.setTextColor(textColor)
        tvCountryPhone.setTextColor(textColor)

        // Set typeface
        tvCountryName.typeface = typeface
        tvCountryCode.typeface = typeface
        tvCountryPhone.typeface = typeface
      }
    }

    init {
      itemView.setOnClickListener {
        onItemClick?.invoke(getItem(bindingAdapterPosition))
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
    val view = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return CountryViewHolder(view)
  }

  override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}