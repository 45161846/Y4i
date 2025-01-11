package com.example.russian.architectured

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.russian.R
import com.example.russian.architectured.MainNavDestinations.StatsFilter.destinationSaver
import com.example.russian.architectured.stats.DrawToTopButton
import com.example.russian.architectured.stats.SearchFilterRow
import com.example.russian.architectured.stats.TopBarState
import com.example.russian.architectured.todo.DummyScreen
import kotlinx.coroutines.launch

@Composable
fun ScreenOverView(
    navigationActions: MainNavigationActions,
    topBarStatus: TopBarState = TopBarState.Hide,
    showBottom: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable (padding: PaddingValues, listState: LazyListState) -> Unit
) {

    val actions by remember(Unit) {
        mutableStateOf(navigationActions)
    }

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            val animationTime = 150

            var currentDestination by rememberSaveable(
                Unit,
                saver = destinationSaver
            ) {
                mutableStateOf(MainNavDestinations.Prac)
            }

            AnimatedVisibility(
                showBottom,
                enter = slideInVertically(tween(animationTime)) { it },
                exit = slideOutVertically(tween(animationTime)) { it }
            ) {
                NavigationBarBottom(currentDestination) {
                    currentDestination = it
                    navigateTo(it, actions)
                }
            }
        },
        topBar = {
            val animationTime = 150
            AnimatedVisibility(
                topBarStatus is TopBarState.Show,
                enter = slideInVertically(tween(animationTime)) { -it },
                exit =
                slideOutVertically(tween(animationTime)) { -it }
            ) {
                SearchFilterRow(
                    onFilterClick = navigationActions::navigateToStatsFilter,
                    onSearch = {
                        if (topBarStatus is TopBarState.Show) {
                            topBarStatus.search(it)
                        }
                    }
                )
            }

        },
        floatingActionButton = {
            if (topBarStatus is TopBarState.Show) {
                DrawToTopButton(lazyListState) {
                    coroutineScope.launch {
                        lazyListState.scrollToItem(1)
                    }
                }
            }
        }
    ) {

        val padding = PaddingValues(
            start = it.calculateStartPadding(LayoutDirection.Ltr),
            end = it.calculateEndPadding(LayoutDirection.Ltr),
            bottom = it.calculateBottomPadding(),
            top = if (topBarStatus is TopBarState.Hide) {
                48.dp
            } else {
                it.calculateTopPadding()
            }
        )

        content(padding, lazyListState)

    }

}

@Composable
fun NavigationBarBottom(
    currentDestination: MainNavDestinations,
    changeSelectedItemIndexTo: (MainNavDestinations) -> Unit
) {

    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            destination = MainNavDestinations.Settings,
            selectedImage = ImageVector.vectorResource(id = R.drawable.settings_filled),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.settings_unfilled)
        ),
        BottomNavigationItem(
            destination = MainNavDestinations.Prac,
            selectedImage = ImageVector.vectorResource(id = R.drawable.brain_outlined),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.brain_black)
        ),
        BottomNavigationItem(
            destination = MainNavDestinations.StatsScreen,
            selectedImage = ImageVector.vectorResource(id = R.drawable.statistics_colored),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.statistics_black)
        ),
    )

    val navColor = MaterialTheme.colorScheme.secondary
    NavigationBar(
        containerColor = navColor,
        modifier = Modifier
            .height(100.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        bottomNavigationItems.forEach { item ->

            val thisIsSelected = item.destination == currentDestination


            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onSecondaryContainer
                ), selected = thisIsSelected,
                onClick = {
                    if (!thisIsSelected) {
                        changeSelectedItemIndexTo(item.destination)
                    }
                },
                interactionSource = MutableInteractionSource(),
                icon = {
                    val currentIcon = if (thisIsSelected) {
                        item.selectedImage
                    } else {
                        item.unselectedImage
                    }
                    Box(
                        modifier = Modifier
                            .size(90.dp, 35.dp)
                            .background(Color.Transparent)
                    ) {
                        Image(
                            imageVector = currentIcon,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()

                        )
                    }
                }
            )
        }
    }

}

private fun navigateTo(
    destination: MainNavDestinations,
    navigationActions: MainNavigationActions
) {
    return when (destination) {
        is MainNavDestinations.Settings -> navigationActions.navigateToSettings()
        is MainNavDestinations.Prac -> navigationActions.navigateToPrac()
        is MainNavDestinations.StatsScreen -> navigationActions.navigateToStatsScreen()
        is MainNavDestinations.StatsFilter -> {}
    }
}

data class BottomNavigationItem(
    val destination: MainNavDestinations,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector
)

@Preview
@Composable
private fun OverlayPreview() {
    ScreenOverView(
        MainNavigationActions(rememberNavController()),
    ) { padd, _ ->
        DummyScreen("Prac", padd)
    }
}