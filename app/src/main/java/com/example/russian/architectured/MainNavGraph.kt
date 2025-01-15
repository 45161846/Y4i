package com.example.russian.architectured

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.architectured.prac.PracScreen
import com.example.russian.architectured.prac.PracViewModel
import com.example.russian.architectured.settings.SettingsScreen
import com.example.russian.architectured.settings.SettingsViewModel
import com.example.russian.architectured.stats.FilterScreen
import com.example.russian.architectured.stats.StatsScreen
import com.example.russian.architectured.stats.StatsViewModel
import com.example.russian.architectured.stats.comp.stats.BottomBarState
import com.example.russian.architectured.stats.comp.stats.TopBarState
import com.example.russian.architectured.stats.testFilterActions
import com.example.russian.architectured.todo.DummyScreen
import com.example.russian.main.ui.draw.test.testActions

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: MainNavDestinations = MainNavDestinations.Prac,
    navActions: MainNavigationActions = remember(navController) {
        MainNavigationActions(navController)
    },
    startGameActivity: (Id) -> Unit
) {

    val statsViewModel: StatsViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val pracViewModel: PracViewModel = hiltViewModel()

    var bottomBarState: BottomBarState by remember {
        mutableStateOf(BottomBarState.Show)
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
        bottomBarState,
        modifier.background(MaterialTheme.colorScheme.surface)
    ) { listState ->
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
                bottomBarState = BottomBarState.Show
                topBar = TopBarState.Hide
                SettingsScreen(
                    settingsViewModel.settingsActions(),
                    settingsViewModel.uiStatesHolder,
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
                bottomBarState = BottomBarState.Show

                var showLocal by remember{
                    mutableStateOf(true)
                }

                topBar = TopBarState.Show.ShowPrac(
                    onLocalClick = {showLocal = true}, onRemoteClick = {showLocal = false}
                )
                PracScreen(
                    pracViewModel,
                    showLocal = showLocal,
                    startGameActivity
                )
            }

            composable<MainNavDestinations.StatsScreen>(
                exitTransition = {
                    exitTransaction()
                },
                enterTransition = {
                    enterTransition()
                }
            ) {
                bottomBarState = BottomBarState.Changeable
                topBar = TopBarState.Show.ShowSearch(
                    statsViewModel::search, navActions::navigateToStatsFilter
                )
                StatsScreen(
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
                bottomBarState = BottomBarState.Hide
                topBar = TopBarState.Hide
                FilterScreen(
                    statsViewModel.filterState.collectAsStateWithLifecycle().value,
                    statsViewModel.actions()
                ){
                    navController.popBackStack()
                }
            }
        }
    }
}