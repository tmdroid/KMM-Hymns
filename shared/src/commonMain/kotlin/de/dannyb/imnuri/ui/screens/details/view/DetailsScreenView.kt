package de.dannyb.imnuri.ui.screens.details.view

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.ui.common.components.Toolbar
import de.dannyb.imnuri.ui.screens.details.ctrl.DetailsScreenCtrl


@Composable
fun DetailsScreenView(
    component: DetailsScreenCtrl,
) = Column(Modifier.fillMaxSize()) {
    val state = component.state.subscribeAsState()

    with(state.value) {
        DetailsToolbar(hymn, isFavorite, onBackAction, onFavoriteAction)
        ShowZoomableHymn(hymn)
    }
}

@Composable
fun ShowZoomableHymn(hymn: Hymn) {
    val fontSize = remember { mutableStateOf(30f) }

    val zoomableModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, _, zoom, _ ->
            fontSize.value *= zoom
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(zoomableModifier),
        contentAlignment = Alignment.TopCenter
    ) {
        ScrollableColumn(hymn, fontSize)
    }
}

@Composable
fun ScrollableColumn(
    hymn: Hymn,
    fontSize: MutableState<Float>,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = hymn.stanzas.joinToString("\n\n"),
            fontSize = fontSize.value.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
    }
}

@Composable
private fun DetailsToolbar(
    hymn: Hymn,
    isFavorite: Boolean,
    onBackAction: () -> Unit,
    onFavoriteAction: () -> Unit
) {
    Toolbar(
        title = hymn.title,
        onNavigateBack = onBackAction,
        rightIcons = {
            IconButton(onClick = {
                onFavoriteAction.invoke()
            }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = "Favorites",
                )
            }
        }
    )
}
