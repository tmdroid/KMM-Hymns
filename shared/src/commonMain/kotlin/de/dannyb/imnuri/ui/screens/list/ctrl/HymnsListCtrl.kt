package de.dannyb.imnuri.ui.screens.list.ctrl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.networking.Api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


interface HymnsListCtrl {
    val hymns: Value<List<Hymn>>
    fun onHymnClicked(hymn: Hymn)
}

class DefaultHymnsListCtrl(
    componentContext: ComponentContext,
    api: Api,
    private val onHymnSelected: (Hymn) -> Unit
) : HymnsListCtrl, ComponentContext by componentContext {

    private var _hymns = MutableValue(emptyList<Hymn>())

    override val hymns: Value<List<Hymn>> get() = _hymns

    init {
        GlobalScope.launch {
            val hymns = api.downloadHymns()
            _hymns.update { hymns }
        }
    }

    override fun onHymnClicked(hymn: Hymn) {
        onHymnSelected.invoke(hymn)
    }
}
