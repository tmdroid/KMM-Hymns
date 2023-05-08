package de.dannyb.imnuri

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.dannyb.imnuri.networking.Api
import de.dannyb.imnuri.ui.screens.details.DetailsScreen
import de.dannyb.imnuri.ui.screens.home.HomeScreen
import de.dannyb.imnuri.util.CurrentScreenState

@Composable
fun App() {
    MaterialTheme {
        val api by remember { mutableStateOf(Api()) }
        var screenState: CurrentScreenState by remember { mutableStateOf(CurrentScreenState.Home) }

        navigateToCorrectScreen(api, screenState) {
            screenState = it
        }
    }
}

@Composable
fun navigateToCorrectScreen(
    api: Api,
    screenState: CurrentScreenState,
    onNavigate: (CurrentScreenState) -> Unit
) {
    when (screenState) {
        is CurrentScreenState.Loading -> {
            onNavigate.invoke(CurrentScreenState.Home)
        }

        is CurrentScreenState.Home -> HomeScreen(api) {
            onNavigate.invoke(CurrentScreenState.Details(it))
        }

        is CurrentScreenState.Details -> {
            val hymn = screenState.hymn
            DetailsScreen(hymn) { onNavigate.invoke(CurrentScreenState.Home) }
        }

        is CurrentScreenState.Settings -> println("Settings")
    }
}
