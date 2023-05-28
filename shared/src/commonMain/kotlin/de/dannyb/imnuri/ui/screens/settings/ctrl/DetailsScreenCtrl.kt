package de.dannyb.imnuri.ui.screens.settings.ctrl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import de.dannyb.imnuri.ui.screens.details.ctrl.ScreenCtrl
import de.dannyb.imnuri.ui.screens.settings.state.SettingsScreenState

interface SettingsScreenCtrl : ScreenCtrl<SettingsScreenState>

class DefaultSettingsScreenCtrl(
    private val componentContext: ComponentContext,
    onBackAction: () -> Unit,
) : SettingsScreenCtrl, ComponentContext by componentContext {

    private val _state = MutableValue(
        SettingsScreenState(
            onBackAction = onBackAction,
        )
    )

    override val state: Value<SettingsScreenState> get() = _state
}