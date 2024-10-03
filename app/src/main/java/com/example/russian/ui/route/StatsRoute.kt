package com.example.russian.ui.route

import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.russian.enums.ScreenFilters
import com.example.russian.enums.ScreenStats
import com.example.russian.ui.draw.StatsScaffold
import com.example.russian.ui.draw.stats.screen.FilterScreen
import com.example.russian.ui.draw.stats.screen.FilterScreenActions
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.SecondaryBackground
import com.example.russian.viewmodel.main.MainViewModel

@Composable
fun StatsRoute(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    bottomBarClick: (Int) -> Unit,
    window: Window
) {

    val statsState = viewModel.uiState().collectAsState()
    val filterData = viewModel.uiFilterData()
    val filterActions = viewModel.actions(ScreenFilters, navController) as FilterScreenActions

    NavHost(
        navController = navController,
        startDestination = ScreenStats
    ) {
        composable<ScreenStats> {
            StatsScaffold(statsState, navController, bottomBarClick)
            window.navigationBarColor = SecondaryBackground.toArgb()
        }

        composable<ScreenFilters>(
            enterTransition = {
                Handler(Looper.getMainLooper()).postDelayed({
                    window.statusBarColor = PrimaryBackground.toArgb()
                }, 300)
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
                window.statusBarColor = SecondaryBackground.toArgb()
                slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down
                )
            }

        ) {
            window.navigationBarColor = PrimaryBackground.toArgb()

            FilterScreen(filterData, filterActions)
        }
    }
}

