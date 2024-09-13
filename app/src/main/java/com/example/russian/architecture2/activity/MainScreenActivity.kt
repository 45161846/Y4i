package com.example.russian.architecture2.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.MyEnumClasses.ScreenFilters
import com.example.russian.MyEnumClasses.ScreenStats
import com.example.russian.R
import com.example.russian.architecture2.application.MyApplication
import com.example.russian.architecture2.ui.draw.DefaultScaffold
import com.example.russian.architecture2.ui.draw.StatsScaffold
import com.example.russian.architecture2.ui.draw.practice.DrawPracticeContent
import com.example.russian.architecture2.ui.draw.settings.DrawSettingsContent
import com.example.russian.architecture2.ui.route.StatsRoute
import com.example.russian.architecture2.ui.state.FilterState
import com.example.russian.architecture2.ui.state.StatsFirstScreenState
import com.example.russian.architecture2.viewmodel.main.StatsViewModel
import com.example.russian.gameClasses.activity.GameActivity
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.SecondaryBackground
import kotlinx.serialization.Serializable

data class BottomNavigationItem(
    val type_of_screen: ScreenType,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector
)

class MainScreenActivity : ComponentActivity() {

    private val statsViewmodel by viewModels<StatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statsViewmodel.setDao((application as MyApplication).statsDao())

        window.statusBarColor = PrimaryBackground.toArgb()
        window.navigationBarColor = SecondaryBackground.toArgb()

        setContent {

            var index by rememberSaveable {
                mutableIntStateOf(1)
            }

            val changeSelectedItemIndex = { it: Int ->

                window.statusBarColor = when (it) {
                    0 -> PrimaryBackground.toArgb()
                    1 -> PrimaryBackground.toArgb()
                    2 -> SecondaryBackground.toArgb()
                    else -> throw IllegalArgumentException(
                        "Cannot chose status bar color. What is the color for the screen number: $it?"
                    )
                }

                index = it
            }

            MainScreen(index, changeSelectedItemIndex)
        }
    }

    @Composable
    private fun MainScreen(index: Int, changeSelectedItemIndex: (Int) -> Unit) {

        when (index) {
            2 -> StatsRoute(rememberNavController(), statsViewmodel, changeSelectedItemIndex, window)

            1 -> PracticeScreen (changeSelectedItemIndex)

            0 -> SettingsScreen(changeSelectedItemIndex)

        }

    }

    @Composable
    private fun SettingsScreen(changeSelectedItemIndex: (Int) -> Unit) {
        DefaultScaffold(
            selectedItemIndex = 0,
            displayableUI = {
                window.statusBarColor = getColor(R.color.dark_background)

                DrawSettingsContent(
                    paddingValues = it
                )
            }, changeSelectedItemIndex
        )
    }

    @Composable
    private fun PracticeScreen(changeSelectedItemIndex: (Int) -> Unit) {
        DefaultScaffold(
            selectedItemIndex = 1,
            displayableUI = { padding ->
                DrawPracticeContent(paddingValues = padding, startGame = { startGame(it) })
            },
            changeSelectedItemIndex
        )
    }

    @Composable
    private fun StatsScreen(
        changeSelectedItemIndex: (Int) -> Unit,
        statsState: State<StatsFirstScreenState>,
        filterState: State<FilterState>
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = ScreenStats
        ) {
            composable<ScreenStats> {
                StatsScaffold(
                    navController = navController,
                    changeSelectedItemIndex = changeSelectedItemIndex,
                    listStats = statsState
                )
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

//                DrawFilterScreen(
//                    filterState,
//                    navController,
//                    statsViewmodel.filterAPI
//                )
            }
        }
    }

    private fun startGame(taskTopic: Int) {
        val intent = Intent(this, GameActivity::class.java)
        val key = this.getString(R.string.game_activity_start_topic_key)
        intent.putExtra(key, taskTopic.toLong() + 1)
        this.startActivity(intent)
    }

}

@Serializable
open class ScreenType

@Serializable
object ScreenTypeSettings : ScreenType()

@Serializable
object ScreenTypePractice : ScreenType()

@Serializable
object ScreenTypeStats : ScreenType()