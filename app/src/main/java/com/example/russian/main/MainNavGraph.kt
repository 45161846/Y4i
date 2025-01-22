package com.example.russian.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.main.prac.local.LocalPracViewModel
import com.example.russian.main.prac.PracScreen
import com.example.russian.main.prac.details.LocalDetailsScreen
import com.example.russian.main.prac.details.RemotePlaylistDetails
import com.example.russian.main.prac.remote.RemotePlaylistViewModel
import com.example.russian.main.settings.SettingsScreen
import com.example.russian.main.settings.SettingsViewModel
import com.example.russian.main.stats.FilterScreen
import com.example.russian.main.stats.StatsScreen
import com.example.russian.main.stats.StatsViewModel
import com.example.russian.main.stats.comp.stats.BottomBarState
import com.example.russian.main.stats.comp.stats.TopBarState
import kotlinx.coroutines.launch

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    pagerState: PagerState = rememberPagerState{ 2 },
    startDestination: MainNavDestinations = MainNavDestinations.Prac,
    navActions: MainNavigationActions = remember(navController) {
        MainNavigationActions(navController)
    },
    startGameActivity: (Id) -> Unit,
    openRateForm: () -> Unit,
    openTelegram: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val statsViewModel: StatsViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val localPracViewModel: LocalPracViewModel = hiltViewModel()
    val remotePracViewModel: RemotePlaylistViewModel = hiltViewModel()

    var bottomBarState: BottomBarState by remember {
        mutableStateOf(BottomBarState.Show)
    }
    var topBar: TopBarState by remember {
        mutableStateOf(TopBarState.Hide)
    }

    var ignoreInsets by remember {
        mutableStateOf(false)
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
        pagerState,
        modifier.background(MaterialTheme.colorScheme.surface),
        ignoreInsets
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
                    settingsViewModel.uiStatesHolder.collectAsStateWithLifecycle().value,
                    openRateForm, openTelegram
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

                topBar = TopBarState.Show.ShowPrac
                PracScreen(
                    localPracViewModel,
                    remotePracViewModel,
                    pagerState,
                    navActions::navigateToRemoteDetails,
                    navActions::navigateToLocalDetails
                )
            }

            composable<MainNavDestinations.DetailsRemote>(
                enterTransition = {
                    ignoreInsets = true
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
                },
                exitTransition = {
                    ignoreInsets = false
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
                }
            ) {
                bottomBarState = BottomBarState.Hide
                topBar = TopBarState.Hide

                RemotePlaylistDetails(
                    remotePracViewModel.detailUiState.collectAsStateWithLifecycle().value
                )

            }

            composable<MainNavDestinations.DetailsLocal>(
                enterTransition = {
                    ignoreInsets = true
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
                },
                exitTransition = {
                    ignoreInsets = false
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
                }
            ) {
                bottomBarState = BottomBarState.Hide
                topBar = TopBarState.Hide

                LocalDetailsScreen(
                    localPracViewModel.detailsUiState.collectAsStateWithLifecycle().value,
                    localPracViewModel::search
                ){//game start
                    startGameActivity(it)
                }
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
                    statsViewModel.filterUiState.collectAsStateWithLifecycle().value,
                    statsViewModel.actions
                ){
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }

                    navController.popBackStack()
                }
            }
        }
    }
}