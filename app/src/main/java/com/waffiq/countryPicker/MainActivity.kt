package com.waffiq.countryPicker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.waffiq.countryPicker.databinding.ActivityMainBinding
import com.waffiq.countryPicker.model.Helper.countryList

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // findViewById<CountryPickerButton>(R.id.cpb).setCountry("MY")
    binding.cpb.setCountry("MY")
    binding.cpb.onCountrySelectedListener = {
      Toast.makeText(this@MainActivity, it.name, Toast.LENGTH_SHORT).show()
    }

    // even if on xml already set default country, it will still changed
    binding.cpbCustom.setCountry("JP")
    findViewById<CountryPickerButton>(R.id.cpb_custom).onCountrySelectedListener={
      Toast.makeText(this@MainActivity, it.name, Toast.LENGTH_SHORT).show()
    }
  }
}