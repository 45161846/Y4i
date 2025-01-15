package com.example.russian.main.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.R
import com.example.russian.architectured.MainNavDestinations
import com.example.russian.main.ui.route.PracRoute
import com.example.russian.main.ui.route.SettingsRoute
import com.example.russian.main.ui.route.StatsRoute
import com.example.russian.main.viewmodel.main.LoadingViewModel
import com.example.russian.main.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainScreenActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels { MainViewModel.Factory }
    private val loadingActivity by viewModels<LoadingViewModel>()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

//        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN)

//        window.decorView.windowInsetsController!!.hide(
//            android.view.WindowInsets.Type.statusBars()
//        )
        enableEdgeToEdge()
        setContent {
            Surface (
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen() {

        val navController = rememberNavController()

        NavHost(
            startDestination = ScreenTypeSettings,
            navController = navController
        ) {

            composable<ScreenTypeSettings> {
                SettingsRoute{navigate(it, navController)}
            }

            composable<ScreenTypePractice> {
                PracRoute(
                    mainViewModel,
                    bottomBarClick = {navigate(it, navController)}
                ) {
                    startGame(it)
                }
            }

            composable<ScreenTypeStats> {
                StatsRoute(navController){navigate(it, navController)}
            }
        }
    }

    private fun startGame(playlistId: Long) {
        val intent = Intent(this, GameActivity::class.java)
        val key = this.getString(R.string.game_activity_start_topic_key)
        intent.putExtra(key, playlistId)
        this.startActivity(intent)
    }

    private fun navigate(index: MainNavDestinations, navController: NavHostController){
        navController.navigate(
            index
        )
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