package com.example.russian.main.ui.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.architectured.MainNavDestinations
import com.example.russian.main.enums.ScreenFilters
import com.example.russian.main.enums.ScreenStats
import com.example.russian.main.ui.draw.StatsScaffold
import com.example.russian.main.ui.draw.stats.screen.FilterScreen
import com.example.russian.main.ui.draw.stats.screen.FilterScreenActions
import com.example.russian.main.viewmodel.main.MainViewModel

@Composable
fun StatsRoute(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    bottomBarClick: (MainNavDestinations) -> Unit,
) {

    val statsState = viewModel.uiState().collectAsState()
    val filterData = viewModel.uiFilterData()
    val filterActions = viewModel.actions(ScreenFilters, navController) as FilterScreenActions

    NavHost(
        navController = navController,
        startDestination = ScreenStats
    ) {
        composable<ScreenStats> {
            StatsScaffold(statsState, bottomBarClick){
                navController.navigate(ScreenFilters)
            }
        }

        composable<ScreenFilters>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down
                )
            }

        ) {
            FilterScreen(filterData, filterActions){
                navController.popBackStack(ScreenStats, false)
            }
        }
    }
}

