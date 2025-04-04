package dev.tomasek.bdaycasino.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tomasek.bdaycasino.R
import dev.tomasek.bdaycasino.components.SpinningWheel
import dev.tomasek.bdaycasino.model.User
import dev.tomasek.bdaycasino.model.WheelSegment
import dev.tomasek.bdaycasino.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LuckyWheelScreen(viewModel: MainViewModel, navController: NavHostController) {
    val user: State<User?> = viewModel.user.observeAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = { Text(text = "Wheel of Fortune") },
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpinningWheel(
                    segments = listOf(
                        WheelSegment("100", Color(0xffd4d2c4), R.raw.spin_sound, 100),
                        WheelSegment("200", Color(0xffa31746), R.raw.spin_sound, 200),
                        WheelSegment("50", Color(0xfffb2410), R.raw.spin_sound, 50),
                        WheelSegment("200", Color(0xfffb4f05), R.raw.spin_sound, 200),
                        WheelSegment("200", Color(0xfff99300), R.raw.spin_sound, 200),
                        WheelSegment("50", Color(0xfff6b600), R.raw.spin_sound, 50),
                        WheelSegment("100", Color(0xfffcfb2d), R.raw.spin_sound, 100),
                        WheelSegment("200", Color(0xffcbe426), R.raw.spin_sound, 200),
                        WheelSegment("50", Color(0xff62aa2d), R.raw.spin_sound, 50),
                        WheelSegment("100", Color(0xff028cca), R.raw.spin_sound, 100),
                        WheelSegment("200", Color(0xff0244fc), R.raw.spin_sound, 200),
                        WheelSegment("100", Color(0xff3d009e), R.raw.spin_sound, 100),
                        WheelSegment("100", Color(0xff8400ab), R.raw.spin_sound, 100),
                        WheelSegment("-100", Color(0xff000000), R.raw.spin_sound, -100),
                    ),
                    viewModel = viewModel,
                    modifier = Modifier
                        .padding(16.dp)
                        .height(300.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    )
}