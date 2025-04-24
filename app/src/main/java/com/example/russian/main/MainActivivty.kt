package com.example.russian.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.coroutineScope
import com.example.russian.R
import com.example.russian.game.GameActivity
import com.example.russian.game.back.initialloading.impl.InitialLoadingExecutor
import com.example.russian.main.data.DatabaseModule
import com.example.russian.main.prac.local.LocalPracViewModel
import com.example.russian.main.prac.remote.RemotePlaylistViewModel
import com.example.russian.main.settings.Settings
import com.example.russian.main.settings.SettingsChanger
import com.example.russian.main.settings.SettingsHolder
import com.example.russian.main.settings.SettingsViewModel
import com.example.russian.main.stats.StatsViewModel
import com.example.russian.main.theme.RussianTheme
import com.example.russian.main.theme.TransparentBlack
import com.example.russian.main.util.GOOGLE_FORM_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(TransparentBlack.toArgb())
        )

//        lifecycle.coroutineScope.launch(Dispatchers.IO) {
//            InitialLoadingExecutor(
//                DatabaseModule.provideLoadingDao(DatabaseModule.provideDataBase(applicationContext)),
//                assets
//            ).execute()
//        }
//
//        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
//
//        val settings = SettingsChanger(Settings(sharedPreferences))

        setContent {

            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            val statsViewModel: StatsViewModel = hiltViewModel()
            val localPracViewModel: LocalPracViewModel = hiltViewModel()
            val remotePracViewModel: RemotePlaylistViewModel = hiltViewModel()

            val theme = settingsViewModel.appThemeCollectable.collectAsStateWithLifecycle()

            RussianTheme(theme.value) {
                MainNavGraph(
                    openRateForm = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_FORM_URL))
                        startActivity(intent)
                    },
                    openTelegram = {
                        //TODO
                    },
                    startGameActivity = {
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra(getString(R.string.game_activity_start_topic_key), it.value)
                        startActivity(intent)
                    },
                    settingsViewModel = settingsViewModel,
                    statsViewModel = statsViewModel,
                    localPracViewModel = localPracViewModel,
                    remotePracViewModel =remotePracViewModel,
                )
            }
        }
    }
}