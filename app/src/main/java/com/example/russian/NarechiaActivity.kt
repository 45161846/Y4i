package com.example.russian

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.telephony.TelephonyManager.ModemErrorException
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.russian.ui.theme.RussianTheme


class NarechiaActivity : ComponentActivity() {

    private var currentApiVersion = 0

    val viewmodel: MyViewModel by viewModels()

    val duration = 1000L
    val vibrationDuration = 200L

    lateinit var wrongSoundMP: MediaPlayer
    lateinit var correctSoundMP: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wrongSoundMP = MediaPlayer.create(this, R.raw.wrong_answer_sound)
        correctSoundMP = MediaPlayer.create(this, R.raw.correct_answer_sound)

        val f = assets.open("my_texts.txt")
        val buffer = ByteArray(f.available())
        f.read(buffer)
        f.close()
        val l = String(buffer, charset("UTF-8")).split("\n")

        for(word: String in l){
            viewmodel.taskAllTexts = viewmodel.taskAllTexts.plus(word)
        }
        viewmodel.updateTask()
        setContent {
            RussianTheme {

                var t by remember {
                    mutableStateOf(ButtonMode.TASK)
                }
                viewmodel.typeOfButton.observe(this){
                    t = it
                }

                var taskText by remember {
                    mutableStateOf(viewmodel.taskText.value!!)
                }
                viewmodel.taskText.observe(this){
                    taskText = it
                }

                Greeting(task = viewmodel.taskObject,
                    r = viewmodel.right.value!!,
                    w = viewmodel.wrong.value!!,
                    typeOfVariant = t
                )

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
    fun Greeting(task: MyTaskNarechia, r: Int, w: Int, typeOfVariant: ButtonMode) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
        ){
            RightWrongRow(r = r, w = w)

            if(task.contextText.isNotEmpty()){
                ContextWord(task.contextText)
            }

            MyTonalButton(col = colorResource(id = R.color.first_answer),
                text = task.options[0]!!,
                isCorrect = task.correctAnswer == 0,
                buttonMode = typeOfVariant
                )

            MyTonalButton(col = colorResource(id = R.color.second_answer),
                text = task.options[1]!!,
                isCorrect = task.correctAnswer == 1,
                buttonMode = typeOfVariant)
            if(task.options.size > 2) {
                MyTonalButton(
                    col = colorResource(R.color.third_answer),
                    text = task.options[2]!!,
                    isCorrect = task.correctAnswer == 2,
                    buttonMode = typeOfVariant
                )
            }

        }

    }

    @Composable
    fun RightWrongRow(r: Int, w: Int){

        val textRight = "Правильно: $r"
        val textWrong = "Ошибок: $w"

        Row (modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(20.dp, 15.dp, 20.dp, 30.dp)

            , horizontalArrangement = Arrangement.SpaceBetween
            , verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = textRight,
                modifier = Modifier
                    .size((12.5 * textRight.length).dp, 50.dp)
                    .background(
                        colorResource(id = R.color.answer_correct_field_back),
                        RoundedCornerShape(15.dp)
                    )
                    .border(
                        2.dp,

                        colorResource(id = R.color.border_answer),
                        RoundedCornerShape(15.dp)
                    )
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically)
                ,
                fontSize = 20.sp
                ,
            )

            Text(
                text = textWrong,
                modifier = Modifier
                    .size((12.5 * textWrong.length).dp, 50.dp)
                    .background(
                        colorResource(id = R.color.answer_wrong_field_back),
                        RoundedCornerShape(15.dp)
                    )
                    .border(
                        2.dp,
                        colorResource(id = R.color.border_answer),
                        RoundedCornerShape(15.dp)
                    )
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically)
                ,
                fontSize = 20.sp
            )
        }
    }

    @Composable
    fun ContextWord(word: String){
        val textSize = 680 / kotlin.math.max(17, word.length)
        Text(
            text = word,
            fontSize = textSize.sp,
            modifier = Modifier
                .background(
                    colorResource(id = R.color.light_background),
                    RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 10.dp, vertical = 3.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }

    @Composable
    fun MyTonalButton(col: Color, text: String, isCorrect: Boolean, buttonMode: ButtonMode = ButtonMode.TASK){

        val borderColorID = getBorderColor(isCorrect, buttonMode)

        FilledTonalButton(
            onClick = {
                if (isCorrect){
                    correct()
                }else{
                    incorrect()
                }
            },
            modifier = Modifier
                .padding(20.dp, 25.dp)
                .size(400.dp, 100.dp)
            ,

            border = BorderStroke(
                if(buttonMode == ButtonMode.TASK){
                    2.dp
                }else if (isCorrect){
                    5.dp
                }else{
                    2.dp
                     },

                colorResource(id = borderColorID)),
            colors = ButtonDefaults.buttonColors(
                containerColor = col,
                contentColor = colorResource(id =
                borderColorID
                )
            )) {
            Text(text = text,
                fontSize = (450 / kotlin.math.max(12, text.length)).sp)
        }
    }

    private fun getBorderColor(isCorrect: Boolean, typeOfVariant: ButtonMode):Int{

        return when(typeOfVariant){
            ButtonMode.TASK -> R.color.border_answer
            ButtonMode.ANSWER_CORRECT -> {
                when(isCorrect){
                    true -> R.color.border_answer_correct
                    false -> R.color.border_answer
                }
            }

            ButtonMode.ANSWER_WRONG -> {
                when(isCorrect){
                    true -> R.color.border_answer_wrong
                    false -> R.color.border_answer
                }
            }
        }

    }

    fun vibrate(){
        val vibrator = this.getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(vibrationDuration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(vibrationDuration)
        }
    }

    private fun incorrect() {
        viewmodel.incorrect()
        wrongSoundMP.start()
        vibrate()
    }

    private fun correct() {
        viewmodel.correct()
        correctSoundMP.start()
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        val testTask = "на*зад;;повернуть"
        RussianTheme {
            Greeting(MyTaskNarechia(testTask), 0, 0, ButtonMode.ANSWER_CORRECT)
        }
    }
}
