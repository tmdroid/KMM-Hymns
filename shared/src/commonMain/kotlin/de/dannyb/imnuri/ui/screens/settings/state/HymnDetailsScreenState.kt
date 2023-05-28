package de.dannyb.imnuri.ui.screens.settings.state

import de.dannyb.imnuri.ui.screens.details.ctrl.ScreenStateModel

data class SettingsScreenState(
    val onBackAction: () -> Unit,
) : ScreenStateModel
