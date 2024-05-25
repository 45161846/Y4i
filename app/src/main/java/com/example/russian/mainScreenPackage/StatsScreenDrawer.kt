package com.example.russian.mainScreenPackage

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.StateOfFocus
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.R
import com.example.russian.database.Word
import com.example.russian.toolPackage.WordToTaskMapper

class StatsScreenDrawer(val viewmodel: MyMainViewModelImpl?){

    @Composable
    fun DrawLoading(loadingAmount: Int){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
        ){
            Text(
                text = "loading($loadingAmount)...",
                color = colorResource(id = R.color.light_background),
                fontSize = 30.sp
            )
        }
    }


    @Composable
    fun DrawContentScreen(listOfWords: List<Word>){

        val state = rememberLazyListState()

        Scaffold(
            topBar = {
                DrawSearchFilterRow()
            },
            floatingActionButton = {
                DrawToTopButton()
            },
            modifier = Modifier
                .fillMaxSize()
        ){
            innerPadding ->
            DrawListOfStats(listOfWords = listOfWords, padding = innerPadding)
        }


    }

    @Composable
    private fun DrawSearchFilterRow(){

        val darkColor = colorResource(id = R.color.dark_background)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    colorResource(id = R.color.light_background),
                    RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
                )
                .padding(8.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            var text by rememberSaveable {
                mutableStateOf("Поиск")
            }
            var focusState by rememberSaveable {
                mutableStateOf(StateOfFocus.EXIT)
            }

            val focusManager = LocalFocusManager.current

            var hideKeyboard  by remember { mutableStateOf(false) }
            TextField(
                onValueChange = {
                    text = it
                },
                value = text,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = darkColor,
                    disabledTextColor = darkColor,
                    errorTextColor = darkColor,
                    unfocusedTextColor = darkColor
                ),
                modifier = Modifier
                    .weight(
                        1F
                    )
                    .onFocusEvent {
                        if (it.hasFocus && focusState != StateOfFocus.SEARCH) {
                            focusState = StateOfFocus.SEARCH
                            text = ""
                        }

                    }
                    .border(
                        2.dp,
                        darkColor,
                        RoundedCornerShape(100)
                    ),

                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusState = if(text.isEmpty()){
                            StateOfFocus.EXIT
                        }else {
                            StateOfFocus.SEARCH
                        }
                        viewmodel!!.search(text)
                        focusManager.clearFocus()
                    }
                ),
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(onClick = {
                            focusState = StateOfFocus.EXIT
                            text = textAfterFocusChange(focusState, text)
                            viewmodel!!.resetCurrentWords()
                            focusManager.clearFocus()
                        }) {
                            Image(
                                modifier = Modifier
                                    .size(28.dp),
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(darkColor)
                            )
                        }
                    }
                }
            )

            if(hideKeyboard){
                focusManager.clearFocus()
                hideKeyboard = false
            }
            IconButton(

                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {

                }
            ){
                Image(
                    modifier = Modifier
                        .size(28.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                    contentDescription = "filter_button",
                    colorFilter = ColorFilter.tint(darkColor)
                )
            }
        }
    }

    private fun textAfterFocusChange(focusState: StateOfFocus, previousText: String): String{
        val defaultText = "Поиск"
        return when(focusState) {
            StateOfFocus.EXIT -> defaultText
            StateOfFocus.SEARCH -> previousText
            StateOfFocus.ENTER -> {
                viewmodel!!.resetCurrentWords()
                String()
            }
        }
    }

    @Composable
    private fun DrawSearchText(){

    }

    @Composable
    private fun DrawFilterButton(){
        IconButton(

            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent
            ),
            onClick = {

            }
        ){
            Image(

                imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                contentDescription = "filter_button",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.dark_background))
            )
        }
    }

    @Composable
    private fun DrawListOfStats(listOfWords: List<Word>, padding: PaddingValues){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
                .padding(padding)
        ) {
            items(
                itemContent = {
                    CardOfStats(listOfWords[it], listOfWords[it].percentage)
                },
                count = listOfWords.size
            )
        }
    }

    @Composable
    private fun DrawToTopButton(){

    }

    @SuppressLint("DefaultLocale")
    @Composable
    private fun CardOfStats(w: Word, winRate: Float){

        val ans = WordToTaskMapper().getDisplayableText(w)

        Row(horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp, 3.dp, 8.dp, 3.dp)
                .background(
                    colorResource(id = R.color.dark_background_light),
                    RoundedCornerShape(5.dp)
                )
        ){
            Text(
                text = ans,
                fontSize = 20.sp,
                color = colorResource(id = R.color.light_background),
                modifier = Modifier
                    .padding(8.dp, 3.dp, 8.dp, 3.dp)
                    .width(200.dp)
            )
            Row(horizontalArrangement = Arrangement.Absolute.Right,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(8.dp, 3.dp, 8.dp, 3.dp)
                    .background(
                        colorResource(id = R.color.dark_background_light),
                        RoundedCornerShape(5.dp)
                    )
            ){

                if(winRate >= 0){
                    Text(
                        color = calculateColor(winRate),
                        text = String.format("%.1f", winRate * 100) + "%",
                        modifier = Modifier
                            .padding(10.dp, 0.dp)
                    )

                }

                Spacer(
                    modifier = Modifier
                        .background(calculateColor(winRate), RoundedCornerShape(100))
                        .size(70.dp, 5.dp)
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier
                    .background(Color.Transparent)
                    .size(10.dp, 2.dp)
                    .padding(10.dp))
            }
        }
    }

    private fun calculateColor(winRate: Float):Color{
        if(winRate < 0){
            return Color(152,152,152)
        }
        return Color((1F - winRate) * 2, winRate * 2, 0F, alpha = 1F)
    }

}
@Composable
@Preview
private fun Preview(){
    StatsScreenDrawer(null).DrawContentScreen(listOfWords = listOf(
        Word("в*век;;___ не забуду", TaskTopic().NARECHI9,1F),
        Word("на голову;;упал снег с ветки|выше", TaskTopic().NARECHI9,0.9F),
        Word("на*право", TaskTopic().NARECHI9,0.2F),
        Word("подобру-поздорову", TaskTopic().NARECHI9,0F),
        Word("на ура;;решение было принято", TaskTopic().NARECHI9,-1F),
        Word("Великий писатель - величественный взгляд", TaskTopic().PARONIM,-1F),
        Word("по*среди", TaskTopic().NARECHI9,-1F),
    ))
}