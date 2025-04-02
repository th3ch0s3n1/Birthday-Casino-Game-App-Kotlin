package dev.tomasek.bdaycasino.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tomasek.bdaycasino.R
import dev.tomasek.bdaycasino.components.NumberInputField
import dev.tomasek.bdaycasino.model.Prize
import dev.tomasek.bdaycasino.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPrizeScreen(viewModel: MainViewModel, navController: NavHostController) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navController.navigate("settings_screen") }) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = stringResource(id = R.string.settings),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrizeInput(viewModel)
                Spacer(modifier = Modifier.height(16.dp))
                PrizeList(viewModel)
            }
        }
    )
}

@Composable
fun PrizeInput(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var contributor by remember { mutableStateOf("") }
    var minimumCreditsToUnlock by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier) {
        TextField(
            value = name,
            singleLine = true,
            onValueChange = {
                name = it
                errorMessage = if (it.isEmpty()) "Please enter a valid name" else null
            },
            label = { Text("Name") },
            isError = errorMessage != null,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface
            )
        )
        errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }

        TextField(
            value = description,
            singleLine = true,
            onValueChange = {
                description = it
                errorMessage = if (it.isEmpty()) "Please enter a valid description" else null
            },
            label = { Text("Description") },
            isError = errorMessage != null,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface
            )
        )
        errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }

        TextField(
            value = contributor,
            singleLine = true,
            onValueChange = {
                contributor = it
                errorMessage = if (it.isEmpty()) "Please enter a valid contributor" else null
            },
            label = { Text("Contributor") },
            isError = errorMessage != null,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface
            )
        )
        errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }

        NumberInputField(
            value = minimumCreditsToUnlock,
            onValueChange = {
                minimumCreditsToUnlock = it
                errorMessage = if (it.isEmpty() || it.toIntOrNull() == null) "Please enter a valid number" else null
            },
            label = "Minimum Credits To Unlock",
            modifier = modifier
        )
        errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }

        Button(
            onClick = {
                val credits = minimumCreditsToUnlock.toIntOrNull()
                if (credits != null && name.isNotEmpty() && description.isNotEmpty() && contributor.isNotEmpty()) {
                    viewModel.addPrize(Prize(name, description, credits, contributor))
                } else {
                    errorMessage = "Please fill in all fields with valid data"
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Add Prize")
        }
    }
}

@Composable
fun PrizeList(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val setting by viewModel.setting.observeAsState()
    val prizes = setting?.prizes ?: emptyList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(prizes.size) { index ->
            PrizeItem(prizes[index], viewModel)
        }
    }
}

@Composable
fun PrizeItem(prize: Prize, viewModel: MainViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Name: ${prize.name}", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground))
        Text(text = "Description: ${prize.description}", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground))
        Text(text = "Minimum Credits: ${prize.minimumCreditsToUnlock}", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground))
        Text(text = "Contributor: ${prize.contributor}", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground))
        Button(
            onClick = { viewModel.deletePrize(prize) },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Delete")
        }
    }
}

