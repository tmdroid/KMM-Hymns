package de.dannyb.imnuri.ui.screens.settings.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import de.dannyb.imnuri.ui.common.components.Toolbar
import de.dannyb.imnuri.ui.screens.settings.ctrl.SettingsScreenCtrl

@Composable
fun SettingsScreenView(ctrl: SettingsScreenCtrl) = Column(Modifier.fillMaxSize()) {
    val state by ctrl.state.subscribeAsState()

    with(state) {
        SettingsToolbar(onBackAction)
    }
}

@Composable
fun SettingsToolbar(onBackAction: () -> Unit) {
    Toolbar(
        title = "Settings",
        onNavigateBack = onBackAction,
        onSettingsIconAction = null,
    )
}
