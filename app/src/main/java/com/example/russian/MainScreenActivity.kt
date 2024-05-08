package com.example.russian

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.ui.theme.RussianTheme


class MainScreenActivity : ComponentActivity() {

    private var currentApiVersion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            RussianTheme {

                Screen()
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }
            }
        }

        currentApiVersion = Build.VERSION.SDK_INT

        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        // This work only for android 4.4+

        // This work only for android 4.4+
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = flags

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            val decorView = window.decorView
            decorView
                .setOnSystemUiVisibilityChangeListener { visibility ->
                    if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                        decorView.systemUiVisibility = flags
                    }
                }
        }

    }

    @SuppressLint("NewApi")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
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
        val context = LocalContext.current

        Button(modifier = Modifier
            .padding(20.dp, 25.dp)
            .size(400.dp, 100.dp),

            onClick = {
            context.startActivity(Intent(context, NarechiaActivity::class.java))
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
    private fun Authors(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
            ,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    copyAuthorLink()
                    toastAfterCopy()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),

                ) {
                AuthorText()
            }

            Button(onClick = {
                copyRedactorLink()
                toastAfterCopy()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),

            ) {
                RedactorText()
            }
        }

    }

    private fun toastAfterCopy(){
        Toast.makeText(this, getString(R.string.toast_on_copy), Toast.LENGTH_SHORT).show()
    }

    private fun copyAuthorLink(){
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            getString(R.string.copy_label),
            "@"+getString(R.string.creator_tg_link)
        )
        clipboard.setPrimaryClip(clip)
    }
    private fun copyRedactorLink(){
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            getString(R.string.copy_label),
            "@"+getString(R.string.redactor_tg_link)
        )
        clipboard.setPrimaryClip(clip)
    }


    @Composable
    private fun AuthorText(){
        Text(
            text = stringResource(id = R.string.creator_tg),
            fontSize = 16.sp,
            color = colorResource(id = R.color.light_background)
        )
    }
    @Composable
    private fun RedactorText(){
        Text(
            text = stringResource(id = R.string.redactor_tg),
            fontSize = 16.sp,
            color = colorResource(id = R.color.light_background)
        )
    }



    @Composable
    @Preview
    private fun ScreenPreview(){
        Screen()
    }

}