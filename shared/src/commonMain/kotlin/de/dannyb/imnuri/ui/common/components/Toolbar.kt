package de.dannyb.imnuri.ui.common.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

data class SearchConfig(
    val value: String,
    val onCharacterTypedAction: (String) -> Unit,
    val onClearAction: () -> Unit,
    val onCloseAction: () -> Unit,
)

@Composable
fun Toolbar(
    title: String = "Imnuri AZS-MR",
    onNavigateBack: (() -> Unit)? = null,
    rightIcons: @Composable (RowScope.() -> Unit)? = null,
    searchConfig: SearchConfig? = null,
) {
    if (searchConfig == null) {
        TitleAppBar(
            title = title,
            onNavigateBack = onNavigateBack,
            rightIcons = rightIcons
        )
    } else {
        SearchAppBar(
            text = searchConfig.value,
            onTextChange = searchConfig.onCharacterTypedAction,
            onCloseClicked = searchConfig.onCloseAction,
        )
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
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            if (onNavigateBack != null) {
                showBackIcon(onNavigateBack)
            }
        },
        elevation = 8.dp,
        actions = {
            rightIcons?.invoke(this)
        },
    )
}

@Composable
private fun showBackIcon(onNavigateBack: () -> Unit) {
    IconButton(
        onClick = { onNavigateBack.invoke() }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
        )
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            )
        )
    }
}