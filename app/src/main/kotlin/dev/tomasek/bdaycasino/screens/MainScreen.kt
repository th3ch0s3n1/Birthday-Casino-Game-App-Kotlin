package dev.tomasek.bdaycasino.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tomasek.bdaycasino.R
import dev.tomasek.bdaycasino.model.Game
import dev.tomasek.bdaycasino.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    val user = viewModel.user.observeAsState()

    val games = listOf(
        Game(name = "Roulette", route = "game_roulette_screen"),
        Game(name = "Wheel of Fortune", route = "game_lucky_wheel_screen")
    )

    Scaffold(
        topBar =  {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navController.navigate("shop_screen") }) {
                        Icon(
                            Icons.Outlined.ShoppingCart,
                            contentDescription = stringResource(id = R.string.settings)
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()) {
        innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            user.value?.let {
                Greeting(name = it.name)
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(games) { game ->
                    GameItem(game = game, navController = navController)
                }
            }
        }
    }
}

@Composable
fun GameItem(game: Game, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(game.route) }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = game.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.greeting, name),
        modifier = modifier
    )
}