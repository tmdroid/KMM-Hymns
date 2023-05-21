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
import androidx.compose.material.Text
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
fun HymnsListScreenView(
    ctrl: HymnsListCtrl,
    onHymnSelected: (Hymn) -> Unit
) = Column(Modifier.fillMaxSize()) {
    Toolbar()
    HymnsList(ctrl, onHymnSelected)
}

@Composable
fun HymnsList(ctrl: HymnsListCtrl, onHymnSelected: (Hymn) -> Unit) {
    val state by ctrl.hymns.subscribeAsState()

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