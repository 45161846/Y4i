package com.example.russian.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.remotelogin.LoginViewModel
import com.example.remotelogin.draw.AuthNavigation
import com.example.remotelogin.values.strings.SHARED_PREFERENCES_KEY
import com.example.russian.main.MainActivity
import com.example.russian.main.theme.RussianTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

        enableEdgeToEdge()
        setContent {

            navController = rememberNavController()
            val viewModel = hiltViewModel<LoginViewModel>()

            val theme = viewModel.appTheme.collectAsStateWithLifecycle()

            RussianTheme (theme.value){
                Surface (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ){
                    AuthNavigation(navController, viewModel) {
                        val intent = Intent(this, MainActivity::class.java)
                        this.startActivity(intent)
                        finish()
                    }
                }

            }

            viewModel.checkLocalCredentials(
                application.getSharedPreferences(
                    SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE
                )
            )
        }
    }
}
