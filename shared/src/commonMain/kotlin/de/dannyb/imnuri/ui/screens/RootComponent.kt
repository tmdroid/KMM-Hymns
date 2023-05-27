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
import de.dannyb.imnuri.networking.HymnsApi
import de.dannyb.imnuri.ui.screens.details.ctrl.DefaultDetailsScreenCtrl
import de.dannyb.imnuri.ui.screens.details.ctrl.DetailsScreenCtrl
import de.dannyb.imnuri.ui.screens.details.view.DetailsScreenView
import de.dannyb.imnuri.ui.screens.list.ctrl.DefaultHymnsListCtrl
import de.dannyb.imnuri.ui.screens.list.ctrl.HymnsListCtrl
import de.dannyb.imnuri.ui.screens.list.view.HymnsListScreenView


interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class ListChild(val ctrl: HymnsListCtrl) : Child()
        class DetailsChild(val ctrl: DetailsScreenCtrl) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val api: HymnsApi
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
            is Config.List -> RootComponent.Child.ListChild(ctrl = itemList(componentContext))
            is Config.Details -> RootComponent.Child.DetailsChild(
                ctrl = itemDetails(componentContext, config)
            )
        }
    }

    private fun itemList(componentContext: ComponentContext): HymnsListCtrl =
        DefaultHymnsListCtrl(
            componentContext = componentContext,
            api = api,
            onHymnSelectedAction = { navigation.push(Config.Details(hymn = it)) }
        )

    private fun itemDetails(
        componentContext: ComponentContext,
        config: Config.Details
    ): DetailsScreenCtrl =
        DefaultDetailsScreenCtrl(
            componentContext = componentContext,
            hymn = config.hymn,
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
            is RootComponent.Child.ListChild -> HymnsListScreenView(
                ctrl = child.ctrl,
            )

            is RootComponent.Child.DetailsChild -> DetailsScreenView(
                ctrl = child.ctrl,
            )
        }
    }
}