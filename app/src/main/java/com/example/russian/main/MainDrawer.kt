package com.example.russian.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.russian.R
import com.example.russian.main.MainNavDestinations.StatsFilter.destinationSaver
import com.example.russian.main.settings.SettingsPreview
import com.example.russian.main.stats.comp.stats.BottomBarState
import com.example.russian.main.stats.comp.stats.DrawToTopButton
import com.example.russian.main.stats.comp.stats.SearchFilterRow
import com.example.russian.main.stats.comp.stats.TopBarState
import com.example.russian.main.theme.RussianTheme
import com.example.russian.main.prac.PracTopBar
import kotlinx.coroutines.launch

@Composable
fun ScreenOverView(
    navigationActions: MainNavigationActions,
    topBarStatus: TopBarState = TopBarState.Hide,
    bottomBarState: BottomBarState = BottomBarState.Show,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    ignoreInsets: Boolean = false,
    content: @Composable (listState: LazyListState) -> Unit
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

            val show by remember(bottomBarState) {
                derivedStateOf {
                    when (bottomBarState) {
                        is BottomBarState.Show -> true
                        is BottomBarState.Hide -> false
                        is BottomBarState.Changeable -> lazyListState.firstVisibleItemIndex == 0
                    }
                }
            }

            var currentDestination by rememberSaveable(
                Unit,
                saver = destinationSaver
            ) {
                mutableStateOf(MainNavDestinations.Prac)
            }


            AnimatedVisibility(
                show,
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
                visible = topBarStatus is TopBarState.Show,
                enter = slideInVertically(tween(animationTime)) { -it },
                exit = slideOutVertically(tween(animationTime)) { -it }
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.secondary,
                            RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                        )
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    when (topBarStatus) {
                        is TopBarState.Show.ShowSearch -> {
                            SearchFilterRow(
                                onFilterClick = navigationActions::navigateToStatsFilter,
                                onSearch = {
                                    topBarStatus.search(it)
                                }
                            )
                        }

                        is TopBarState.Show.ShowPrac -> {
                            PracTopBar(
                                pagerState,
                                coroutineScope
                            )
                        }

                        else -> {}
                    }
                }

            }

        },
        floatingActionButton = {
            if (topBarStatus is TopBarState.Show) {
                DrawToTopButton(lazyListState) {
                    coroutineScope.launch {
                        lazyListState.scrollToItem(0)
                    }
                }
            }
        }
    ) {

        val boxModifier = Modifier
            .fillMaxSize()
            .run {
                if (topBarStatus is TopBarState.Show) {
                    padding(top = it.calculateTopPadding())
                } else if(ignoreInsets.not()) {
                    windowInsetsPadding(WindowInsets.statusBars)
                } else{
                    padding()
                }
            }
        Box(
            modifier = boxModifier,
        ) {
            content(lazyListState)
        }

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
            alwaysShow = true,
            selectedImage = ImageVector.vectorResource(id = R.drawable.settings_filled),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.settings_unfilled)
        ),
        BottomNavigationItem(
            destination = MainNavDestinations.Prac,
            alwaysShow = true,
            selectedImage = ImageVector.vectorResource(id = R.drawable.brain_outlined),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.brain_black)
        ),
        BottomNavigationItem(
            destination = MainNavDestinations.StatsScreen,
            alwaysShow = false,
            selectedImage = ImageVector.vectorResource(id = R.drawable.statistics_colored),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.statistics_black)
        ),
    )

    val navColor = MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .background(Color.Transparent)
    ) {
        NavigationBar(
            containerColor = navColor,
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(100))
            ,
        ) {
            bottomNavigationItems.forEach { item ->

                val thisIsSelected = item.destination == currentDestination


                NavigationBarItem(
                    modifier = Modifier
                        .defaultMinSize(1.dp, 1.dp)
                        .wrapContentSize()
                    ,
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


                        Image(
                            imageVector = currentIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 12.dp)

                        )

                    }
                )
            }
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
        is MainNavDestinations.DetailsRemote -> navigationActions.navigateToRemoteDetails()
        is MainNavDestinations.DetailsLocal -> navigationActions.navigateToLocalDetails()
    }
}

data class BottomNavigationItem(
    val destination: MainNavDestinations,
    val alwaysShow: Boolean,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector
)

@SuppressLint("UnrememberedMutableState")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun OverlayPreview() {
    RussianTheme {
        ScreenOverView(
            MainNavigationActions(rememberNavController()),
            TopBarState.Show.ShowPrac,
            pagerState = rememberPagerState { 2 },
            modifier = Modifier,
        ) { _ ->
            SettingsPreview()
        }
    }
}