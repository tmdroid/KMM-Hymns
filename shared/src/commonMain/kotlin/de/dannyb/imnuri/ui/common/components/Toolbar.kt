package de.dannyb.imnuri.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class SearchConfig(
    val value: String,
    val onCharacterTypedAction: (String) -> Unit,
    val onClearAction: () -> Unit,
    val onCloseSearchAction: () -> Unit,
)

@Composable
fun Toolbar(
    title: String = "Imnuri AZS-MR",
    onNavigateBack: (() -> Unit)? = null,
    rightIcons: @Composable (RowScope.() -> Unit)? = null,
    searchConfig: SearchConfig? = null,
) {
    if (searchConfig == null) {
        TitleAppBar(title, onNavigateBack, rightIcons)
    } else {
        SearchableAppBar(searchConfig, checkNotNull(onNavigateBack))
    }
}

@Composable
private fun TitleAppBar(
    title: String,
    onNavigateBack: (() -> Unit)?,
    rightIcons: @Composable (RowScope.() -> Unit)?,
) {
    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = MaterialTheme.colors.background,
        navigationIcon = {
            if (onNavigateBack != null) {
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
        },
    )
}

@Composable
private fun SearchableAppBar(searchConfig: SearchConfig, onNavigateBack: () -> Unit) =
    with(searchConfig) {
        TopAppBar {
            TextField(
                value = value,
                onValueChange = onCharacterTypedAction,
                trailingIcon = {
                    if (value.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "clear",
                            modifier = Modifier.clickable { onClearAction.invoke() }
                        )
                    }
                }
            )
        }
    }
