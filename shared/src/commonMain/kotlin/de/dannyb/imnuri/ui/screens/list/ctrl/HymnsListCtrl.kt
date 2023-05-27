package de.dannyb.imnuri.ui.screens.list.ctrl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.networking.HymnsApi
import de.dannyb.imnuri.ui.common.components.SearchConfig
import de.dannyb.imnuri.ui.screens.details.ctrl.ScreenCtrl
import de.dannyb.imnuri.ui.screens.list.state.HymnsListScreenState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


interface HymnsListCtrl : ScreenCtrl<HymnsListScreenState>

class DefaultHymnsListCtrl(
    componentContext: ComponentContext,
    api: HymnsApi,
    onHymnSelectedAction: (Hymn) -> Unit
) : HymnsListCtrl, ComponentContext by componentContext {

    private var searchConfig: SearchConfig? = null
    private var searchText: String = ""

    private val _state = MutableValue(
        HymnsListScreenState(
            title = "Imnuri AZS-MR",
            hymns = emptyList(),
            searchConfig = null,
            onHymnSelectedAction = onHymnSelectedAction,
            onSearchIconSelectedAction = ::onSearchSelected,
        )
    )

    override val state: Value<HymnsListScreenState>
        get() = _state

    init {
        GlobalScope.launch {
            val hymns = api.downloadHymns()
            _state.update { it.copy(hymns = hymns) }
        }
    }

    private fun onSearchSelected() {
        fun updateState() {
            _state.update { it.copy(searchConfig = searchConfig) }
        }

        searchConfig = SearchConfig(
            value = searchText,
            onCharacterTypedAction = {
                updateSearchConfig(it)
                updateState()
            },
            onClearAction = {
                updateSearchConfig("")
                updateState()
            },
            onCloseSearchAction = {
                searchConfig = null
                updateState()
            }
        )

        _state.update { it.copy(searchConfig = searchConfig) }
    }

    private fun updateSearchConfig(text: String? = null) {
        searchConfig = if (text == null) null else searchConfig?.copy(value = text)
    }
}
