package de.dannyb.imnuri.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.networking.Api
import de.dannyb.imnuri.ui.common.components.Toolbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface HymnsListComponent {
    val hymns: Value<List<Hymn>>
    fun onHymnClicked(hymn: Hymn)
}

class DefaultHymnsListComponent(
    componentContext: ComponentContext,
    api: Api,
    private val onHymnSelected: (Hymn) -> Unit
) : HymnsListComponent, ComponentContext by componentContext {

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

@Composable
fun HomeScreen(
    component: HymnsListComponent,
    onHymnSelected: (Hymn) -> Unit
) = Column(Modifier.fillMaxSize()) {
    Toolbar()
    HymnsList(component, onHymnSelected)
}

@Composable
fun HymnsList(component: HymnsListComponent, onHymnSelected: (Hymn) -> Unit) {
    val state by component.hymns.subscribeAsState()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(state) { index, item ->
            ImnElement(index, item, onHymnSelected)
        }
    }
}

@Composable
fun ImnElement(index: Int, item: Hymn, onHymnSelected: (Hymn) -> Unit) {
    val content = "${index + 1}. ${item.title}"

    Divider(
        modifier = Modifier.fillMaxWidth().height(1.dp),
        color = Color.LightGray
    )

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(35.dp)
        .clickable { onHymnSelected(item) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 30.dp),
                text = content,
            )
        }
    }
}