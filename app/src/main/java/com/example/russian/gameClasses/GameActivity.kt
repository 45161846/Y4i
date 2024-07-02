package com.example.russian.gameClasses

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.ButtonMode
import com.example.russian.MyEnumClasses.Letters
import com.example.russian.MyEnumClasses.MyTimerMode
import com.example.russian.MyEnumClasses.SortTypesEnum
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.R
import com.example.russian.gameClasses.ydareni9.Ydareni9Task
import com.example.russian.toolPackage.SingleLetter
import com.example.russian.toolPackage.TaskInterface
import com.example.russian.toolPackage.WordToTaskMapper
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class GameActivity: ComponentActivity() {

    lateinit var viewmodel: MyGameViewModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application

        val key = this.getString(R.string.game_activity_start_topic_key)
        val taskTopic = intent.extras!!.getInt(key)

        viewmodel = MyGameViewModelImpl(
            app,
            taskTopic,
            GameSettings(
                50,
                MyTimerMode.MODE_NO_TIME,
                timerLimit = 0,
                sortedBy = SortTypesEnum.RANDOM,
                delayBetweenAnswerAndNextTask = 1000L
            )
        )

        setContent {

            var t by remember {
                mutableStateOf(viewmodel.typeOfButton.value)
            }

            viewmodel.typeOfButton.observe(this){
                t = it
            }

            var loading by remember {
                mutableStateOf(viewmodel.isLoadingInProcess.value)
            }
            viewmodel.isLoadingInProcess.observe(this){
                loading = it
            }

            //GameActivityDrawerClass().LoadingScreen(loading!!, viewmodel.repository.currentWords.size)

            if(loading!!){
                LoadingScreen(loading!!, viewmodel.repository.currentWords.size)
            }else{
                if(t == ButtonMode.TASK){
                    viewmodel.createTask()
                }
                Greeting(
                    viewmodel.currentTask!!,
                    r = viewmodel.right,
                    w = viewmodel.wrong,
                    typeOfVariant = t!!,
                )
            }
        }

    }

    @Composable
    fun Greeting(
        task: TaskInterface,
        r: Int,
        w: Int,
        typeOfVariant: ButtonMode,

    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
        ){
            RightWrongRow(r = r, w = w)

            if(viewmodel.topic == TaskTopic().YDARENI9){
                Ydareni9Content(task = task, typeOfVariant = typeOfVariant)
            }else{
                GameContent(task = task, typeOfVariant = typeOfVariant)
            }

        }
        BackButton()
    }

    private @Composable
    fun Ydareni9Content(task: TaskInterface, typeOfVariant: ButtonMode) {
        if(task is Ydareni9Task){
            DrawYdareni9Lettres(task = task, typeOfVariant)
        }else{
            Text(text = "Oops...")
        }
    }

    @Composable
    fun DrawYdareni9Lettres(task: Ydareni9Task, typeOfVariant: ButtonMode){
        val lettersInARow = 9
        val letters = task.letters
        val rowsSize = ceil(letters.size.toDouble() / lettersInARow).toInt()
        val rows = List(rowsSize){
            letters.slice(it * lettersInARow until min((it + 1) * lettersInARow, letters.size))
        }
        LazyColumn(
            userScrollEnabled = false,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
        ) {
            items(
                rowsSize,
                itemContent = {
                    DrawLineOfLetters(l = rows[it], typeOfVariant = typeOfVariant)
                }
            )
        }



    }

    @Composable
    fun DrawLineOfLetters(l: List<SingleLetter>, typeOfVariant: ButtonMode){
        LazyRow(
            userScrollEnabled = false,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent)
        ) {
            items(
                l.size,
                itemContent = {
                    DrawSingleLetter(s = l[it], typeOfVariant = typeOfVariant)
                }
            )
        }
    }

    @Composable
    fun DrawSingleLetter(s: SingleLetter, typeOfVariant: ButtonMode){
        if(s.type == Letters.SOGLASNA9){
            DrawSogl(s = s)
        }else{
            DrawGlas(s = s, typeOfVariant = typeOfVariant)
        }
    }

    @Composable
    fun DrawSogl(s: SingleLetter){
        val letterColor = colorResource(id = R.color.ydar_sogl_text)

        Text(
            color = letterColor,
            text = s.letter,
            fontSize = 42.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(align = Alignment.CenterVertically),
            )

    }

    @Composable
    fun DrawGlas(s: SingleLetter, typeOfVariant: ButtonMode){
        val letterColor = if(typeOfVariant == ButtonMode.TASK){
            colorResource(id = R.color.border_answer)
        }else{
            if(s.type == Letters.BESYDARNA9){
                colorResource(id = R.color.border_answer)
            }else{
                if(typeOfVariant == ButtonMode.ANSWER_WRONG){
                    colorResource(id = R.color.border_answer_wrong)
                }else{
                    colorResource(id = R.color.border_answer_correct)
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(3.dp)
                .size(50.dp, 80.dp)
                .background(
                    color = colorResource(id = R.color.ydar_back),
                    RoundedCornerShape(25)
                )
                .border(3.5.dp, letterColor, RoundedCornerShape(25))
        ){
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxSize()
                ,
                onClick = {
                    if (typeOfVariant == ButtonMode.TASK) {
                        if (s.type == Letters.YDARNA9) {
                            viewmodel.correctAnswer()
                        } else {
                            viewmodel.incorrectAnswer()
                        }
                    }
                }
            ){
                Text(
                    color = letterColor,
                    text = s.letter,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(align = Alignment.CenterVertically),
                )
            }
        }
    }


    @Composable
    fun GameContent(
        task: TaskInterface,
        typeOfVariant: ButtonMode
    ){
        if(task.getTaskText().isNotEmpty()){
            ContextWord(task.getTaskText().replace("_", " "))
        }

        MyTonalButton(col = colorResource(id = R.color.first_answer),
            text = task.getPosibleVariants()[0].replace("_", " "),
            isCorrect = task.isCorrect(0),
            buttonMode = typeOfVariant,

            )

        MyTonalButton(col = colorResource(id = R.color.second_answer),
            text = task.getPosibleVariants()[1].replace("_", " "),
            isCorrect = task.isCorrect(1),
            buttonMode = typeOfVariant,

            )
        if(task.getPosibleVariants().size > 2) {
            MyTonalButton(
                col = colorResource(R.color.third_answer),
                text = task.getPosibleVariants()[2].replace("_", " "),
                isCorrect = task.isCorrect(2),
                buttonMode = typeOfVariant,

                )
        }
    }

    @Composable
    fun BackButton(){
        IconButton(

            onClick = {
                onBackPressed()
            },
            modifier = Modifier
                .size(80.dp)
        ){
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.back_comback_hom_svgrepo_com),
                contentDescription = "back icon",
                modifier = Modifier
                    .fillMaxSize()
            )
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
        val textSize = 680 / max(17, word.length)
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
    fun MyTonalButton(
        col: Color,
        text: String,
        isCorrect: Boolean,
        buttonMode: ButtonMode = ButtonMode.TASK,
    ){

        val borderColorID = getBorderColor(isCorrect, buttonMode)

        FilledTonalButton(
            onClick = {
                if (isCorrect){
                    viewmodel.correctAnswer()
                    Log.d("myTag_buttons", "correct")
                }else{
                    viewmodel.incorrectAnswer()
                    Log.d("myTag_buttons", "incorrect")
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

                colorResource(id = borderColorID)
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = col,
                contentColor = colorResource(id =
                borderColorID
                )
            )) {
            Text(text = text,
                fontSize = (450 / max(12, text.length)).sp)
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

    @Composable
    fun LoadingScreen(loadingState: Boolean, repositorySize: Int){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
        ){
            Text(
                text = if(loadingState) {
                    "loading..."
                }else{
                    "completed ${repositorySize}"
                },
                color = colorResource(id = R.color.light_background),
                fontSize = 30.sp
            )
        }
    }

    override fun onResume() {
        super.onResume()
        window.statusBarColor = getColor(R.color.dark_background)
        window.navigationBarColor = getColor(R.color.dark_background)
    }


    @Composable
    @Preview
    fun GamePreview(){

        GameContent(task = WordToTaskMapper().toContextTask("Выдача заказ - отдача оружие - передача телевизионная - раздача призов"), typeOfVariant = ButtonMode.ANSWER_CORRECT)

    }
}