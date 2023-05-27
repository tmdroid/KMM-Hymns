package de.dannyb.imnuri.ui.screens.list.view

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.ui.common.components.Toolbar
import de.dannyb.imnuri.ui.screens.list.ctrl.HymnsListCtrl


@Composable
fun HymnsListScreenView(ctrl: HymnsListCtrl) = Column(Modifier.fillMaxSize()) {
    val state by ctrl.state.subscribeAsState()

    with(state) {
        SearchableToolbar(title, onSearchIconSelectedAction)
        HymnsList(hymns, onHymnSelectedAction)
    }
}

@Composable
private fun SearchableToolbar(title: String, onSearchIconSelectedAction: () -> Unit) {
    Toolbar(
        title = title,
        rightIcons = {

            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.clickable { onSearchIconSelectedAction.invoke() }
            )
        },
    )
}

@Composable
fun HymnsList(hymns: List<Hymn>, onHymnSelected: (Hymn) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(hymns) { index, item ->
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
        .clickable { onHymnSelected.invoke(item) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = content,
            )
        }
    }
}