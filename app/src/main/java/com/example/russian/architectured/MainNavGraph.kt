package com.example.russian.architectured

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.architectured.settings.SettingsScreen
import com.example.russian.architectured.settings.SettingsViewModel
import com.example.russian.architectured.stats.StatsScreen
import com.example.russian.architectured.stats.StatsViewModel
import com.example.russian.architectured.stats.TopBarState
import com.example.russian.architectured.todo.DummyScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: MainNavDestinations = MainNavDestinations.Prac,
    navActions: MainNavigationActions = remember(navController) {
        MainNavigationActions(navController)
    }
) {

    val statsViewModel: StatsViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    var showBottom by remember {
        mutableStateOf(true)
    }
    var topBar: TopBarState by remember {
        mutableStateOf(TopBarState.Hide)
    }

    val animationTime = 150
    val exitTransaction = {
        fadeOut(tween(animationTime))
    }
    val enterTransition = {
        fadeIn(tween(animationTime))
    }

    ScreenOverView(
        navActions,
        topBar,
        showBottom,
        modifier.background(MaterialTheme.colorScheme.surface)
    ) { padding, listState ->
        NavHost(
            startDestination = startDestination,
            navController = navController,
        ) {

            composable<MainNavDestinations.Settings>(
                exitTransition = {
                    exitTransaction()
                },
                enterTransition = {
                    enterTransition()
                }
            ) {
                showBottom = true
                topBar = TopBarState.Hide
                SettingsScreen(
                    settingsViewModel.settingsActions(),
                    padding,
                    settingsViewModel.uiStatesHolder
                )
            }

            composable<MainNavDestinations.Prac>(
                exitTransition = {
                    exitTransaction()
                },
                enterTransition = {
                    enterTransition()
                }
            ) {
                showBottom = true
                topBar = TopBarState.Hide
                DummyScreen("Prac", padding)
            }

            composable<MainNavDestinations.StatsScreen>(
                exitTransition = {
                    exitTransaction()
                },
                enterTransition = {
                    enterTransition()
                }
            ) {
                showBottom = true
                topBar = TopBarState.Show(
                    statsViewModel::search, navActions::navigateToStatsFilter
                )
                StatsScreen(
                    padding,
                    viewModel = statsViewModel,
                    listState = listState
                )
            }

            composable<MainNavDestinations.StatsFilter>(
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,tween(animationTime)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,tween(animationTime)
                    )
                }
            )

            {
                showBottom = false
                topBar = TopBarState.Hide
                DummyScreen("Filter")
            }
        }
    }
}