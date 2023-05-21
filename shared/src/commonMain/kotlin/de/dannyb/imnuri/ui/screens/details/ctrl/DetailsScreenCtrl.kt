package de.dannyb.imnuri.ui.screens.details.ctrl

import com.arkivanov.decompose.ComponentContext
import de.dannyb.imnuri.model.Hymn


interface DetailsScreenCtrl {
    val hymn: Hymn
    fun onFavoriteClicked(hymn: Hymn)
    fun onBackClicked()
}

class DefaultDetailsScreenCtrl(
    private val componentContext: ComponentContext,
    override val hymn: Hymn,
    private val onFavoriteAction: (Hymn) -> Boolean,
    private val onBackAction: () -> Unit,
) : DetailsScreenCtrl {

    override fun onFavoriteClicked(hymn: Hymn) {
        val result = onFavoriteAction.invoke(hymn)
        println("Is favorite: $result")
    }

    override fun onBackClicked() {
        onBackAction.invoke()
    }
}