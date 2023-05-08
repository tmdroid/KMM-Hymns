package de.dannyb.imnuri.ui.screens.details

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.ui.common.components.Toolbar


@Composable
fun DetailsScreen(
    hymn: Hymn,
    onNavigateBack: () -> Unit
) = Column(Modifier.fillMaxSize()) {

    var zoomSize by remember { mutableStateOf(0f) }

    Toolbar(onNavigateBack = onNavigateBack, title = hymn.title)

    Surface(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    println("Detected gesture: $centroid, $pan, $zoom, $rotation")
                }
            }
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

            Text(
                text = hymn.stanzas.joinToString("\n\n\n"),
                fontSize = 20.sp,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

        }

    }
}