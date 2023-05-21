package de.dannyb.imnuri.ui.screens.details

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.ui.common.components.Toolbar


interface HymnDetailsComponent {
    val hymn: Hymn
    fun onFavoriteClicked(hymn: Hymn)
    fun onBackClicked()
}

class DefaultHymnDetailsComponent(
    private val componentContext: ComponentContext,
    override val hymn: Hymn,
    private val onFavoriteAction: (Hymn) -> Boolean,
    private val onBackAction: () -> Unit,
) : HymnDetailsComponent {

    override fun onFavoriteClicked(hymn: Hymn) {
        val result = onFavoriteAction.invoke(hymn)
        println("Is favorite: $result")
    }

    override fun onBackClicked() {
        onBackAction.invoke()
    }
}

@Composable
fun DetailsScreen(
    component: HymnDetailsComponent,
    onFavoriteAction: (Hymn) -> Boolean,
) = Column(Modifier.fillMaxSize()) {

    var fontSize by remember { mutableStateOf(20f) }
    var isFavorite by remember { mutableStateOf(false) }

    Toolbar(
        title = component.hymn.title,
        onNavigateBack = { component.onBackClicked() },
        rightIcons = {
            IconButton(onClick = {
                isFavorite = !isFavorite
                onFavoriteAction.invoke(component.hymn)
            }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = "Favorites",
                )
            }
        }
    )

    val zoomableModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, _, zoom, _ ->
            fontSize *= zoom
        }
    }

    Surface(
        modifier = zoomableModifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

            Text(
                text = component.hymn.stanzas.joinToString("\n\n"),
                fontSize = fontSize.sp,
                modifier = zoomableModifier.align(alignment = Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

        }

    }
}
