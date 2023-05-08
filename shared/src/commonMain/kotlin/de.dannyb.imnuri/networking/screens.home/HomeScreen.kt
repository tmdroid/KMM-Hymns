package de.dannyb.imnuri.networking.screens.home

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
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.dannyb.imnuri.networking.Api
import model.Hymn

@Composable
fun HomeScreen(api: Api) = Column(Modifier.fillMaxSize()) {

    Toolbar()

    var hymns by remember { mutableStateOf<List<Hymn>>(emptyList()) }

    LaunchedEffect(true) {
        try {
            hymns = api.downloadHymns()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(hymns) { index, item ->
            ImnElement(index, item)
        }
    }
}

@Composable
fun Toolbar() {
    TopAppBar(
        title = { Text(text = "Imnuri AZS-MR") },
        backgroundColor = Color.White
    )
}

@Composable
fun ImnElement(index: Int, item: Hymn) {
    val content = "${index + 1}. ${item.title}"

    Divider(
        modifier = Modifier.fillMaxWidth().height(1.dp),
        color = Color.LightGray
    )

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(35.dp)
        .clickable { println("Hymn ${item.number} has been clicked") }
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