package de.dannyb.imnuri.ui.screens.details.ctrl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.ui.screens.details.state.HymnDetailsScreenState

interface ScreenStateModel

interface ScreenCtrl<T : ScreenStateModel> {
    val state: Value<T>
}

interface DetailsScreenCtrl : ScreenCtrl<HymnDetailsScreenState>

class DefaultDetailsScreenCtrl(
    private val componentContext: ComponentContext,
    hymn: Hymn,
    onBackAction: () -> Unit,
) : DetailsScreenCtrl, ComponentContext by componentContext {
    private var isFavorite = false

    private val _state = MutableValue(
        HymnDetailsScreenState(
            hymn = hymn,
            isFavorite = isFavorite,
            onFavoriteAction = ::onFavoriteAction,
            onBackAction = onBackAction,
        )
    )

    private fun onFavoriteAction() {
        isFavorite = !isFavorite
        _state.update { it.copy(isFavorite = isFavorite) }
    }

    override val state: Value<HymnDetailsScreenState>
        get() = _state
}