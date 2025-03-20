package dev.tomasek.bdaycasino.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import dev.tomasek.bdaycasino.R
import dev.tomasek.bdaycasino.viewmodel.MainViewModel

@Composable
fun UserRegister(viewModel: MainViewModel, modifier: Modifier = Modifier, navController: NavHostController) {
    val user by viewModel.user.observeAsState()
    val setting by viewModel.setting.observeAsState()
    var text by remember { mutableStateOf(user?.name ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val error = stringResource(id = R.string.name_empty_error)

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = text,
            singleLine = true,
            onValueChange = {
                text = it
                if (it.isBlank()) {
                    errorMessage = error
                } else {
                    errorMessage = null
                    viewModel.setUsername(it)
                }
            },
            label = { Text(stringResource(id = R.string.enter_name)) },
            isError = errorMessage != null
        )
        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        // If username is empty ==> Don't display
        if (text.isNotEmpty()) {
            Button(
                shape = ButtonDefaults.elevatedShape,
                onClick = {
                    viewModel.setCredits()
                    navController.navigate("main_screen")
                }
            ) {
                Text(text = stringResource(id = R.string.message_continue))
            }
        }
    }
}