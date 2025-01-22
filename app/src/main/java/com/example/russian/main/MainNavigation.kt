package com.example.russian.main

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

    override fun navigateToRemoteDetails() {
        navController.navigate(MainNavDestinations.DetailsRemote)
    }

    override fun navigateToLocalDetails() {
        navController.navigate(MainNavDestinations.DetailsLocal)
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
    fun navigateToRemoteDetails()
    fun navigateToLocalDetails()
    fun navigateToStatsScreen()
    fun navigateToStatsFilter()
}