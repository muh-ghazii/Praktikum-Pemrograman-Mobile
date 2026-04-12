package com.contoh.tipcalculatorxml

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.contoh.tipcalculatorxml.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class TipViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var tipPercentage: Double
        get() {
            val saved = savedStateHandle.get<String>("tip_percentage") ?: "15"
            return saved.toInt() / 100.0
        }
        set(value) {
            val asInt = (value * 100).toInt()
            savedStateHandle["tip_percentage"] = asInt.toString()
        }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TipViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupDropdown()
        calculateTip()

        binding.billAmountEditText.doAfterTextChanged { calculateTip() }
        binding.roundUpSwitch.setOnCheckedChangeListener { _, _ -> calculateTip() }
    }

    private fun setupDropdown() {
        val tipLabels = resources.getStringArray(R.array.tip_options)
        val tipValues = resources.getIntArray(R.array.tip_values)

        val displayOptions = mutableListOf<String>()
        for (label in tipLabels) {
            displayOptions.add(label)
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            displayOptions
        )
        val savedPercent = (viewModel.tipPercentage * 100).toInt()
        val savedIndex = tipValues.indexOfFirst { it == savedPercent }
        val displayText = if (savedIndex >= 0) tipLabels[savedIndex] else tipLabels[0]
        binding.tipOptions.setText(displayText, false)
        binding.tipOptions.setAdapter(adapter)

        binding.tipOptions.setOnItemClickListener { _, _, position, _ ->
            val selectedInt = tipValues[position]
            val selectedDouble = selectedInt / 100.0
            viewModel.tipPercentage = selectedDouble
            calculateTip()
        }
    }

    private fun calculateTip() {
        val inputString: String = binding.billAmountEditText.text.toString()
        val cost: Double = inputString.toDoubleOrNull() ?: 0.0

        if (cost == 0.0) {
            displayTip(0.0)
            return
        }

        var tip = cost * viewModel.tipPercentage
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance(java.util.Locale.US).format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}