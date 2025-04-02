package dev.tomasek.bdaycasino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.tomasek.bdaycasino.screens.AddPrizeScreen
import dev.tomasek.bdaycasino.screens.LuckyWheelScreen
import dev.tomasek.bdaycasino.screens.MainScreen
import dev.tomasek.bdaycasino.screens.RouletteScreen
import dev.tomasek.bdaycasino.screens.SettingsScreen
import dev.tomasek.bdaycasino.screens.ShopScreen
import dev.tomasek.bdaycasino.screens.WelcomeScreen
import dev.tomasek.bdaycasino.ui.theme.BirthdayCasinoTheme
import dev.tomasek.bdaycasino.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BirthdayCasinoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome_screen") {
                    composable("welcome_screen") { WelcomeScreen(viewModel, navController) }
                    composable("main_screen") { MainScreen(viewModel, navController) }

                    composable("game_roulette_screen") { RouletteScreen(viewModel, navController) }
                    composable("game_lucky_wheel_screen") { LuckyWheelScreen(viewModel, navController) }

                    composable("add_prize_screen") { AddPrizeScreen(viewModel, navController) }
                    composable("settings_screen") { SettingsScreen(viewModel, navController) }
                    composable("shop_screen") { ShopScreen(viewModel, navController) }
                }
            }
        }
    }
}