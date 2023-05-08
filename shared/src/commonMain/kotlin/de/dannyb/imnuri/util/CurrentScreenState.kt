package de.dannyb.imnuri.util

import de.dannyb.imnuri.model.Hymn

sealed class CurrentScreenState {

    object Loading : CurrentScreenState()

    object Home : CurrentScreenState()

    data class Details(val hymn: Hymn) : CurrentScreenState()

    object Settings : CurrentScreenState()

}