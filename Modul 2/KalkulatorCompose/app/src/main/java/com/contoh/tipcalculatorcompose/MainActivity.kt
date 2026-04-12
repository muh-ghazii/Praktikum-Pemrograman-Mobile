package com.contoh.tipcalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen() {
    var amountInput by rememberSaveable { mutableStateOf("") }
    var selectedTipPercentage by rememberSaveable { mutableStateOf(15) }
    var roundUp by rememberSaveable { mutableStateOf(true) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    val tipPercentages = listOf(15, 18, 20)
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var tip = amount * (selectedTipPercentage / 100.0)
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

    val maroonColor = Color(0xFF8E3E59)
    val containerBgColor = Color(0xFFEBE5EA)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.calculate_tip),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Bill Amount") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = "Payment Icon",
                    tint = Color.DarkGray
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = containerBgColor,
                focusedContainerColor = containerBgColor,
                unfocusedIndicatorColor = maroonColor,
                focusedIndicatorColor = maroonColor,
                unfocusedLabelColor = maroonColor,
                focusedLabelColor = maroonColor
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = "$selectedTipPercentage%",
                onValueChange = {},
                readOnly = true,
                label = { Text("Tip Percentage") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_percent),
                        contentDescription = "Percent Icon",
                        tint = Color.DarkGray
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = containerBgColor,
                    focusedContainerColor = containerBgColor,
                    unfocusedIndicatorColor = maroonColor,
                    focusedIndicatorColor = maroonColor
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipPercentages.forEach { percentage ->
                    DropdownMenuItem(
                        text = { Text("$percentage%") },
                        onClick = {
                            selectedTipPercentage = percentage
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Round up tip?",
                color = Color(0xFF49454F)
            )
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = maroonColor
                )
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(id = R.string.tip_amount, formattedTip),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF001D2D),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}