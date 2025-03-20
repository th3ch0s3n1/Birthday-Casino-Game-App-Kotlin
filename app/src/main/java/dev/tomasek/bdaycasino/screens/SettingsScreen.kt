package dev.tomasek.bdaycasino.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import dev.tomasek.bdaycasino.R
import dev.tomasek.bdaycasino.viewmodel.MainViewModel
import dev.tomasek.bdaycasino.components.NumberInputField
import kotlinx.coroutines.delay

@Composable
fun SettingsScreen(viewModel: MainViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.settings))
        PrizeInput(viewModel)
        CreditsInput(viewModel)
    }
}

@Composable
fun PrizeInput(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val setting = viewModel.setting.observeAsState()
    var text by remember { mutableStateOf(setting.value?.maxPrize.toString()) }

    NumberInputField(
        value = text,
        onValueChange = {
            text = it
            if (it.isEmpty()) viewModel.setMaxPrize(0)
            if (it.isNotEmpty()) viewModel.setMaxPrize(it.toInt())
        },
        label = stringResource(id = R.string.max_prize),
        modifier = modifier
    )
}

@Composable
fun CreditsInput(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val setting = viewModel.setting.observeAsState()
    var text by remember { mutableStateOf(setting.value?.startingCredits.toString()) }

    NumberInputField(
        value = text,
        onValueChange = {
            text = it
            if (it.isEmpty()) viewModel.setStartingCredits(0)
            if (it.isNotEmpty()) viewModel.setStartingCredits(it.toInt())
        },
        label = stringResource(id = R.string.starting_credits),
        modifier = modifier
    )
}