package com.example.russian.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.coroutineScope
import com.example.russian.R
import com.example.russian.game.GameActivity
import com.example.russian.game.back.initialloading.impl.InitialLoadingExecutor
import com.example.russian.main.data.DatabaseModule
import com.example.russian.main.theme.RussianTheme
import com.example.russian.main.theme.TransparentBlack
import com.example.russian.main.util.GOOGLE_FORM_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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

        setContent {
            RussianTheme {
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
                    }
                )
            }
        }
    }
}