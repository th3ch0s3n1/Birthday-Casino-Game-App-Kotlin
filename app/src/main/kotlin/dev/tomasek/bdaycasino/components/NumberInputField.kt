package dev.tomasek.bdaycasino.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun NumberInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier) {
        TextField(
            value = value,
            singleLine = true,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    onValueChange(it)
                    errorMessage = null
                } else {
                    errorMessage = "Please enter a valid number"
                }
            },
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorMessage != null
        )
        errorMessage?.let {
            Text(text = it.toString(), color = MaterialTheme.colorScheme.error)
        }
    }
}