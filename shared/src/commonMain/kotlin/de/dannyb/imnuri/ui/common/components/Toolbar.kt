package de.dannyb.imnuri.ui.common.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Toolbar(
    title: String = "Imnuri AZS-MR",
    onNavigateBack: (() -> Unit)? = null,
    rightIcons: @Composable (RowScope.() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = Color.White,
        navigationIcon = {
            if(onNavigateBack != null) {
                IconButton(
                    onClick = { onNavigateBack.invoke() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
        elevation = 8.dp,
        actions = {
            rightIcons?.invoke(this)
        }
    )
}