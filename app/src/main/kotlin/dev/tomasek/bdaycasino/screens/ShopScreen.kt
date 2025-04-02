package dev.tomasek.bdaycasino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import dev.tomasek.bdaycasino.R
import dev.tomasek.bdaycasino.model.Prize
import dev.tomasek.bdaycasino.viewmodel.MainViewModel
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(viewModel: MainViewModel, navController: NavHostController) {
    val setting by viewModel.setting.observeAsState()
    val prizes = setting?.prizes ?: emptyList()
    val user by viewModel.user.observeAsState()
    val userPrizeCredits = user?.prize ?: 0
    val inventory = user?.inventory ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.shop)) }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(text = "Credits: $userPrizeCredits", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Available Prizes:", style = MaterialTheme.typography.headlineSmall)
                }
                items(prizes) { prize ->
                    ShopPrizeItem(prize = prize, viewModel = viewModel)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Purchased Items:", style = MaterialTheme.typography.headlineSmall)
                }
                items(inventory) { prize ->
                    PurchasedItem(prize = prize)
                }
            }
        }
    )
}

@Composable
fun ShopPrizeItem(prize: Prize, viewModel: MainViewModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = prize.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = prize.description, style = MaterialTheme.typography.bodySmall)
            Text(text = "Price: ${prize.minimumCreditsToUnlock} credits", style = MaterialTheme.typography.bodySmall)
            Button(
                onClick = { viewModel.purchasePrize(prize) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Purchase")
            }
        }
    }
}

@Composable
fun PurchasedItem(prize: Prize, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = prize.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = prize.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}