package dev.tomasek.bdaycasino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import dev.tomasek.bdaycasino.R
import dev.tomasek.bdaycasino.components.NumberInputField
import dev.tomasek.bdaycasino.model.Prize
import dev.tomasek.bdaycasino.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPrizeScreen(viewModel: MainViewModel, navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = { Text(text = stringResource(id = R.string.winning_prizes)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_prize))
            }
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
                PrizeList(viewModel)
            }
        }
    )

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { showDialog = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    PrizeInput(viewModel)
                }
            }
        }
    }
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

        Spacer(modifier = Modifier.height(16.dp))

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
        columns = GridCells.Fixed(1),
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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            IconButton(onClick = { viewModel.deletePrize(prize) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = prize.name,
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = prize.description,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = "Minimum Credits: ${prize.minimumCreditsToUnlock}",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = "Contributor: ${prize.contributor}",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}

