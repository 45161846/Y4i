package com.example.russian

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.ui.theme.RussianTheme


class MainScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            RussianTheme {
                Screen()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

    }

    @Composable
    private fun Screen(){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background)))
        {
            NarechiaButton()
        }
        BottomText()
    }

    @Composable
    fun BottomText(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
            ,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.latest_version),
                fontSize = 16.sp,
                color = colorResource(id = R.color.light_background)
            )
        }
    }

    @Composable
    private fun NarechiaButton(){

        Button(modifier = Modifier
            .padding(20.dp, 25.dp)
            .size(400.dp, 100.dp),

            onClick = {
            this.startActivity(Intent(this, NarechiaActivity::class.java))
        }) {
            Text(text = "Наречия",
                fontSize = 30.sp)
        }
        
        Button(onClick = {},
            modifier = Modifier
                .padding(20.dp, 25.dp)
                .size(400.dp, 100.dp)
        ) {
            Text(text = "Пока не готово",
                fontSize = 30.sp)
        }
    }

    @Composable
    @Preview
    private fun ScreenPreview(){
        Screen()
    }

}