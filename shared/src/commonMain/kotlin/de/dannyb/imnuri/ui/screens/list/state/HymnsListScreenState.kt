package de.dannyb.imnuri.ui.screens.list.state

import com.arkivanov.decompose.value.Value
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.ui.common.components.SearchConfig
import de.dannyb.imnuri.ui.screens.details.ctrl.ScreenStateModel

data class HymnsListScreenState(
    val title: String,
    val hymns: Value<List<Hymn>>,
    val onHymnSelectedAction: (Hymn) -> Unit,
    val onSearchIconSelectedAction: () -> Unit,
    val searchConfig: SearchConfig? = null
): ScreenStateModel