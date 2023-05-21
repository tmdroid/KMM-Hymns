package de.dannyb.imnuri.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.networking.Api
import de.dannyb.imnuri.ui.screens.details.ctrl.DefaultDetailsScreenCtrl
import de.dannyb.imnuri.ui.screens.details.ctrl.DetailsScreenCtrl
import de.dannyb.imnuri.ui.screens.details.view.DetailsScreen
import de.dannyb.imnuri.ui.screens.home.DefaultHymnsListComponent
import de.dannyb.imnuri.ui.screens.home.HomeScreen
import de.dannyb.imnuri.ui.screens.home.HymnsListComponent


interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class ListChild(val component: HymnsListComponent) : Child()
        class DetailsChild(val component: DetailsScreenCtrl) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val api: Api
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.List,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = _childStack

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is Config.List -> RootComponent.Child.ListChild(component = itemList(componentContext))
            is Config.Details -> RootComponent.Child.DetailsChild(
                component = itemDetails(componentContext, config)
            )
        }
    }

    private fun itemList(componentContext: ComponentContext): HymnsListComponent =
        DefaultHymnsListComponent(
            componentContext = componentContext,
            api = api,
            onHymnSelected = { navigation.push(Config.Details(hymn = it)) }
        )

    private fun itemDetails(
        componentContext: ComponentContext,
        config: Config.Details
    ): DetailsScreenCtrl =
        DefaultDetailsScreenCtrl(
            componentContext = componentContext,
            hymn = config.hymn,
            onFavoriteAction = { true },
            onBackAction = { navigation.pop() }
        )

    private sealed interface Config : Parcelable {
        @Parcelize
        object List : Config

        @Parcelize
        data class Details(val hymn: Hymn) : Config
    }
}

@Composable
fun RootContent(component: RootComponent, modifier: Modifier) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        modifier = modifier.fillMaxSize(),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.ListChild -> HomeScreen(
                component = child.component,
                onHymnSelected = { hymn -> child.component.onHymnClicked(hymn) }
            )

            is RootComponent.Child.DetailsChild -> DetailsScreen(
                component = child.component,
                onFavoriteAction = { hymn -> println("clicked on favorite for $hymn").let { true } }
            )
        }
    }
}