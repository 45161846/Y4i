package com.example.russian.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController
import com.example.russian.application.MyApplication
import com.example.russian.R
import com.example.russian.ui.draw.DefaultScaffold
import com.example.russian.ui.draw.practice.DrawPracticeContent
import com.example.russian.ui.draw.settings.DrawSettingsContent
import com.example.russian.ui.draw.test.testSettingScreenData
import com.example.russian.ui.route.SettingsRoute
import com.example.russian.ui.route.StatsRoute
import com.example.russian.viewmodel.main.StatsViewModel
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

        statsViewmodel.setDao(application as MyApplication)

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

            0 -> SettingsRoute(rememberNavController(), statsViewmodel, changeSelectedItemIndex, window)

        }

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

@Serializable
object ScreenSettings : ScreenType()