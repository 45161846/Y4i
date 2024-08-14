package com.example.russian.mainScreenPackage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.MyEnumClasses.ScreenFilters
import com.example.russian.MyEnumClasses.ScreenStats
import com.example.russian.R
import com.example.russian.architecture.CustomApplication
import com.example.russian.architecture.StatsScreenViewModelImpl
import com.example.russian.architecture.repository.LocalWordRepositoryImpl
import com.example.russian.architecture2.application.MyApplication
import com.example.russian.gameClasses.activity.GameActivity
import com.example.russian.mainScreenPackage.screenDrawers.DefaultScaffold
import com.example.russian.mainScreenPackage.screenDrawers.StatsScaffoldNoRepo
import com.example.russian.mainScreenPackage.screenDrawers.practice.DrawPracticeContent
import com.example.russian.mainScreenPackage.screenDrawers.settings.DrawSettingsContent
import com.example.russian.mainScreenPackage.screenDrawers.stats.DrawFilterScreen
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.SecondaryBackground
import kotlinx.serialization.Serializable

data class BottomNavigationItem(
    val type_of_screen: ScreenType,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector
)

class MainScreenActivity : ComponentActivity() {

    private val statsViewmodel by viewModels<StatsScreenViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as MyApplication

        statsViewmodel.setArguments(LocalWordRepositoryImpl(
            app.wordDao()
        ))

        setContent{

            var index by rememberSaveable {
                mutableIntStateOf(1)
            }

            val changeSelectedItemIndex = { it : Int ->

                window.statusBarColor = when(it){
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
    private fun MainScreen(index: Int, changeSelectedItemIndex: (Int) -> Unit){

        when(index){
            2 -> {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenStats
                ) {
                    composable<ScreenStats> {
                        StatsScaffoldNoRepo(
                            navController = navController,
                            scope = rememberCoroutineScope(),
                            onSearch = {
                                statsViewmodel.changeFilter(it)
                                statsViewmodel.applyFilter() },
                            changeSelectedItemIndex = changeSelectedItemIndex,
                            contentListFlow = statsViewmodel.wordsOnScreen
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
                        DrawFilterScreen(
                            filter = statsViewmodel.filter,
                            navController = navController,
                            onChangeFilterSettings = {
                                statsViewmodel.applyFilter()
                            }
                        )
                    }
                }
            }
            1 -> {

                DefaultScaffold(
                    selectedItemIndex = index,
                    displayableUI = {padding ->
                        DrawPracticeContent(paddingValues = padding, startGame = { startGame(it) })
                                    },
                    changeSelectedItemIndex)
            }
            0 -> {

                DefaultScaffold(selectedItemIndex = index, displayableUI = {

                    window.statusBarColor = getColor(R.color.dark_background)

                    DrawSettingsContent(
                        paddingValues = it
                    )
                }, changeSelectedItemIndex)
            }
        }

    }




    @Composable
    fun EnterAnimation(content: @Composable () -> Unit) {
        AnimatedVisibility(
            visibleState = MutableTransitionState(
                initialState = false
            ).apply { targetState = true },
            modifier = Modifier,
            enter = slideInVertically(
                initialOffsetY = { 2000 }
            )
            + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically(
                targetOffsetY = {2000}
            ) + fadeOut(),
        ) {
            content()
        }
    }

    private fun startGame(taskTopic: Int){
        val intent = Intent(this, GameActivity::class.java)
        val key = this.getString(R.string.game_activity_start_topic_key)
        intent.putExtra(key, taskTopic.toLong() + 1)
        this.startActivity(intent)
    }

}

@Serializable
open class ScreenType

@Serializable
object ScreenTypeSettings: ScreenType()
@Serializable
object ScreenTypePractice: ScreenType()
@Serializable
object ScreenTypeStats: ScreenType()