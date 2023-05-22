package de.dannyb.imnuri.ui.screens.details.state

import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.ui.screens.details.ctrl.ScreenStateModel

data class HymnDetailsScreenState(
    val hymn: Hymn,
    val isFavorite: Boolean,
    val onFavoriteAction: () -> Unit,
    val onBackAction: () -> Unit,
) : ScreenStateModel
