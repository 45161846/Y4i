package com.example.russian.architectured

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import com.example.russian.main.ui.theme.MainTheme
import com.example.russian.main.ui.theme.RussianTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RussianTheme {
                Surface(modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                ) {
                    MainNavGraph()
                }
            }
        }

    }

}