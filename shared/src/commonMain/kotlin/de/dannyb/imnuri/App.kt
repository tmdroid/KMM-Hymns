package de.dannyb.imnuri

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import de.dannyb.imnuri.networking.Api
import de.dannyb.imnuri.ui.screens.DefaultRootComponent
import de.dannyb.imnuri.ui.screens.RootContent

@Composable
fun App() {
    val api by remember { mutableStateOf(Api()) }

    showHymnsScreen(api)
}

@Composable
private fun showHymnsScreen(api: Api) {
    val lifecycle by remember { mutableStateOf(LifecycleRegistry()) }
    val componentContext by remember { mutableStateOf(DefaultComponentContext(lifecycle)) }

    val component = DefaultRootComponent(componentContext, api)

    MyApplicationTheme {
        Surface(color = MaterialTheme.colors.background) {
            RootContent(component = component, modifier = Modifier.fillMaxSize())
        }
    }
}
