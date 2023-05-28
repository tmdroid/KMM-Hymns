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

    private var searchText = MutableValue("")
    private val allHymns = MutableValue(emptyList<Hymn>())

    private val searchedHymns = allHymns.filterByValue(searchText) { hymns, text ->
        if (text.isEmpty()) allHymns.value else {
            val asNumber = text.toIntOrNull()
            val filterer: (Hymn) -> Boolean = if (asNumber != null) {
                { hymn -> hymn.number.toString().startsWith(text) }
            } else { hymn -> hymn.title.contains(text) }

            allHymns.value.filter(filterer)
        }
    }

    private val _state = MutableValue(
        HymnsListScreenState(
            title = "Imnuri AZS-MR",
            hymns = searchedHymns,
            searchConfig = null,
            onHymnSelectedAction = onHymnSelectedAction,
            onSearchIconSelectedAction = ::onSearchSelected,
        )
    )

    override val state: Value<HymnsListScreenState> get() = _state

    init {
        GlobalScope.launch {
            val hymns = api.downloadHymns()
            allHymns.update { hymns }
        }
    }

    private fun onSearchSelected() {
        val searchConfig = SearchConfig(
            value = searchText.value,
            onCharacterTypedAction = { text ->
                searchText.update { text }
                updateScreenState { copy(searchConfig = searchConfig?.copy(value = text)) }
            },
            onClearAction = {
                updateScreenState { copy(searchConfig = searchConfig?.copy(value = "")) }
            },
            onCloseAction = {
                updateScreenState { copy(searchConfig = null) }
            }
        )

        updateScreenState { copy(searchConfig = searchConfig) }
    }

    private fun updateScreenState(cb: HymnsListScreenState.() -> HymnsListScreenState) {
        _state.update(cb)
    }
}

private fun <LEFT : Any, RIGHT : Any> Value<List<LEFT>>.filterByValue(
    value: Value<RIGHT>,
    combiner: (List<LEFT>, RIGHT) -> List<LEFT>
): Value<List<LEFT>> {
    var left: List<LEFT> = this.value
    var right: RIGHT = value.value
    val out = MutableValue(left)

    fun update() {
        out.update { combiner.invoke(left, right) }
    }

    subscribe {
        left = it
        update()
    }

    value.subscribe {
        right = it
        update()
    }

    return out
}
