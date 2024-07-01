package com.example.russian.mainScreenPackage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Transaction
import com.example.russian.MyEnumClasses.ScreenFilters
import com.example.russian.MyEnumClasses.ScreenStats
import com.example.russian.R
import com.example.russian.gameClasses.GameActivity
import com.example.russian.mainScreenPackage.screenDrawers.DefaultScaffold
import com.example.russian.mainScreenPackage.screenDrawers.DrawFilterScreen
import com.example.russian.mainScreenPackage.screenDrawers.DrawPracticeContent
import com.example.russian.mainScreenPackage.screenDrawers.DrawSettingsContent
import com.example.russian.mainScreenPackage.screenDrawers.StatsScaffold
import kotlinx.serialization.Serializable

data class BottomNavigationItem(
    val type_of_screen: ScreenType,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector
)

class MainScreenActivity : ComponentActivity() {

    private lateinit var viewmodel: MyMainViewModelImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application

        viewmodel = MyMainViewModelImpl(
            app
        )

        setContent{
            MainScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        window.navigationBarColor = getColor(R.color.dark_background_2)
    }

    @Composable
    private fun MainScreen(){

        var index by remember {
            mutableIntStateOf(viewmodel.selectedScreenIndex.value ?: 1)
        }

        viewmodel.selectedScreenIndex.observe(this){
            index = it ?: 1
            window.statusBarColor = if(it == 2){
                viewmodel.setRepository()
                getColor(R.color.light_background)
            }else{
                viewmodel.clearRepository()
                getColor(R.color.dark_background)
            }
        }

        val changeSelectedItemIndex = { it : Int ->
            viewmodel.selectedScreenIndex.value = it
        }

        when(index){
            2 -> {

                val navController = rememberNavController()
                val owner = this



                NavHost(
                    navController = navController,
                    startDestination = ScreenStats
                ) {
                    composable<ScreenStats> {
                        StatsScaffold(
                            selectedItemIndex = index,
                            navController = navController,
                            scope = rememberCoroutineScope(),
                            onSearch = { viewmodel.search(it) },
                            changeSelectedItemIndex = changeSelectedItemIndex,
                            repo = viewmodel.repository,
                            owner = owner,
                        )
                    }

                    composable<ScreenFilters>(
                        enterTransition = {
                            Handler(Looper.getMainLooper()).postDelayed({
                                window.statusBarColor = getColor(R.color.dark_background)
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
                            window.statusBarColor = getColor(R.color.light_background)
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = EaseIn),
                                towards = AnimatedContentTransitionScope.SlideDirection.Down
                            )
                        }


                    ) {
                        DrawFilterScreen(
                            filter = viewmodel.getFilterSettings(),
                            navController = navController,
                            onChangeFilterSettings = {
                                viewmodel.setFilterSettings(it)
                                viewmodel.search()
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
            0 -> DefaultScaffold(selectedItemIndex = index, displayableUI = {

                window.statusBarColor = getColor(R.color.dark_background)

                DrawSettingsContent(
                    paddingValues = it
                )
            }, changeSelectedItemIndex)
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
        intent.putExtra(key, taskTopic)
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