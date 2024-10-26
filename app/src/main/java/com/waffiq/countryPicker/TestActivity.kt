package com.waffiq.countryPicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.waffiq.countryPicker.R
import com.waffiq.countryPicker.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

  private lateinit var binding: ActivityTestBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityTestBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // findViewById<CountryPickerButton>(R.id.cpb).setCountry("MY")
    binding.cpbTest.setCountry("MY")
    binding.cpbTest.onCountrySelectedListener = {
      Toast.makeText(this@TestActivity, it.name, Toast.LENGTH_SHORT).show()
    }
  }
}