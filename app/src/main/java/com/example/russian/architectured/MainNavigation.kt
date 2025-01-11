package com.example.russian.architectured

import androidx.navigation.NavHostController

class MainNavigationActions(
    private val navController: NavHostController
) : MainNavigation {

    override fun navigateToSettings() {
        navigate(MainNavDestinations.Settings)
    }

    override fun navigateToPrac() {
        navigate(MainNavDestinations.Prac)
    }

    override fun navigateToStatsScreen() {
        navigate(MainNavDestinations.StatsScreen)
    }

    override fun navigateToStatsFilter() {
        navController.navigate(MainNavDestinations.StatsFilter)
    }

    private fun navigate(destination: MainNavDestinations) {
        navController.navigate(destination) {
            popUpTo(0)
        }

    }
}

interface MainNavigation {
    fun navigateToSettings()
    fun navigateToPrac()
    fun navigateToStatsScreen()
    fun navigateToStatsFilter()
}