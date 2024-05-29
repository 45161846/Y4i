package com.example.russian.mainScreenPackage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.MyEnumClasses.StateOfFocus
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.R
import com.example.russian.gameClasses.GameActivity
import com.example.russian.ui.theme.RussianTheme
import kotlinx.serialization.Serializable


data class BottomNavigationItem(
    val type_of_screen: ScreenType,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector
)

class MainScreenActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    private lateinit var viewmodel: MyMainViewModelImpl

    var selectedItemIndex = mutableIntStateOf(1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application

        viewmodel = MyMainViewModelImpl(
            app
        )

        setContent{
            MainScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        window.navigationBarColor = getColor(R.color.dark_background_2)
    }

    @Composable
    private fun MainScreen(){
        navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBarBottom(navController = navController)
            },
            content = { innerPadding ->
                MainScreenContent(innerPadding)
            },
            topBar = {
                DrawTopBar(selectedItemIndex.intValue)
            },
            floatingActionButton = {
                DrawFloatingButton(selectedItemIndex.intValue)
            }
        )
    }

    @Composable
    private fun DrawTopBar(selectedItemIndex: Int) {
        if(selectedItemIndex == 2){
            DrawSearchFilterRow()
            window.statusBarColor = getColor(R.color.light_background)
        }else{
            window.statusBarColor = getColor(R.color.dark_background)
        }
    }
    @Composable
    private fun DrawFloatingButton(selectedItemIndex: Int) {
        //TODO create toTop button
    }

    @Composable
    private fun MainScreenContent(paddingValues: PaddingValues) {

        NavHost(navController = navController,
            startDestination = ScreenTypePractice,
            modifier = Modifier.fillMaxSize()
        ){
            composable<ScreenTypePractice> {
                viewmodel.cancelLoading()
                viewmodel.clearRepository()
                ScreenPractice(paddingValues)
            }
            composable<ScreenTypeSettings> {
                viewmodel.cancelLoading()
                viewmodel.clearRepository()
                ScreenSettings(paddingValues)
            }
            composable<ScreenTypeStats> {
                viewmodel.setRepository()
                ScreenStats(paddingValues)
            }
        }

    }



    @SuppressLint("MutableCollectionMutableState")
    @Composable
    private fun ScreenStats(paddingValues: PaddingValues) {
        var loading by remember{
            mutableStateOf(viewmodel.loadingProcessesAmount.value)
        }

        viewmodel.loadingProcessesAmount.observe(this){
            loading = it
        }

        var itemsOnScreen by remember {
            mutableStateOf(viewmodel.repository.currentWords.value)
        }

        viewmodel.repository.currentWords.observe(this){
            itemsOnScreen = it
        }

        if(loading!! == 0) {
            StatsScreenDrawer(paddingValues).DrawContentScreen(itemsOnScreen!!.toList())
        }else{
            StatsScreenDrawer(paddingValues).DrawLoading(loading!!)
        }
    }

    @Composable
    private fun ScreenSettings(paddingValues: PaddingValues) {
        val backColor = colorResource(id = R.color.dark_background)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(paddingValues)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(color = Color.White,
                text = "Settings",
            )
        }
    }

    @Composable
    private fun ScreenPractice(paddingValues: PaddingValues) {

        val backColor = colorResource(id = R.color.dark_background)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(paddingValues)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            OpenGameButtonButton(
                description = "Наречия",
                taskTopic = TaskTopic().NARECHI9
            )
            OpenGameButtonButton(
                description = "Паронимы",
                taskTopic = TaskTopic().PARONIM
            )

            OpenGameButtonButton(
                description = "Ударения",
                taskTopic = TaskTopic().YDARENI9
            )
        }

    }

    @Composable
    private fun NavigationBarBottom(navController: NavController){

        val bottomNavigationItems = listOf(
            BottomNavigationItem(
                type_of_screen = ScreenTypeSettings,
                selectedImage = ImageVector.vectorResource(id = R.drawable.settings_filled),
                unselectedImage = ImageVector.vectorResource(id = R.drawable.settings_unfilled)
            ),
            BottomNavigationItem(
                type_of_screen = ScreenTypePractice,
                selectedImage = ImageVector.vectorResource(id = R.drawable.brain_outlined),
                unselectedImage = ImageVector.vectorResource(id = R.drawable.brain_black)
            ),
            BottomNavigationItem(
                type_of_screen = ScreenTypeStats,
                selectedImage = ImageVector.vectorResource(id = R.drawable.statistics_colored),
                unselectedImage = ImageVector.vectorResource(id = R.drawable.statistics_black)
            ),
        )

        val navColor = colorResource(id = R.color.dark_background_2)
        NavigationBar(
            containerColor = navColor,
            modifier = Modifier
                .height(50.dp)
                //.padding(5.dp, 0.dp, 5.dp, 5.dp)
                .clip(RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp))
        ) {
            bottomNavigationItems.forEachIndexed{index, item ->
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorResource(id = R.color.dark_background_3)
                        //indicatorColor = Color.Transparent
                    ),
                    selected = selectedItemIndex.intValue == index,
                    onClick = {
                        if(selectedItemIndex.intValue != index) {
                            selectedItemIndex.intValue = index
                            navController.navigate(item.type_of_screen)
                        }
                    },
                    icon = {
                        val currentIcon = if(index == selectedItemIndex.intValue){
                            item.selectedImage
                        }else{
                            item.unselectedImage
                        }
                        Box (
                            modifier = Modifier
                                .size(90.dp, 35.dp)
                                .background(Color.Transparent)
                        ){
                            Image(imageVector = currentIcon,
                                contentDescription = "screen ${item.type_of_screen}",
                                modifier = Modifier
                                    .fillMaxSize()

                            )
                        }
                    })
            }
        }
    }

    @Composable
    private fun OpenGameButtonButton(
        taskTopic: Int,
        description: String
    ){

        Button(modifier = Modifier
            .padding(20.dp, 25.dp)
            .size(400.dp, 100.dp),

            onClick = {
                startGame(taskTopic)
            }

        ) {
            Text(text = description,
                fontSize = 30.sp)
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
                    if(text.isNotEmpty()){
                        viewmodel.search(text)
                    }else{
                        viewmodel.resetCurrentWords()
                    }
                },
                value = text,
                colors = searchFieldColors(),
                modifier = Modifier
                    .weight(
                        1F
                    )
                    .onFocusEvent {
                        if (it.hasFocus && focusState != StateOfFocus.SEARCH) {
                            focusState = StateOfFocus.SEARCH
                            text = String()
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
                        focusManager.clearFocus()
                    }
                ),
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                trailingIcon = {

                    IconButton(onClick = {
                        focusState = StateOfFocus.EXIT
                        text = textAfterFocusChange(focusState, text)
                        viewmodel.resetCurrentWords()
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
            )

            if(hideKeyboard){
                focusManager.clearFocus()
                hideKeyboard = false
            }

            DrawFilterButton()
        }
    }

    @Composable
    private fun DrawFilterButton(){
        val darkColor = colorResource(id = R.color.dark_background)
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

    @Composable
    private fun searchFieldColors(): TextFieldColors{
        val darkColor = colorResource(id = R.color.dark_background)

        return TextFieldDefaults.colors(
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
        )
    }

    private fun textAfterFocusChange(focusState: StateOfFocus, previousText: String): String{
        val defaultText = "Поиск"
        return when(focusState) {
            StateOfFocus.EXIT -> defaultText
            StateOfFocus.SEARCH -> previousText
            StateOfFocus.ENTER -> {
                viewmodel.resetCurrentWords()
                String()
            }
        }
    }

    private fun startGame(taskTopic: Int){
        val intent = Intent(this, GameActivity::class.java)
        val key = this.getString(R.string.game_activity_start_topic_key)
        intent.putExtra(key, taskTopic)
        this.startActivity(intent)
    }

    @Composable
    @Preview
    private fun ScreenPreview(){
        ScreenStats(PaddingValues(20.dp))
    }

}

@Serializable
open class ScreenType

@Serializable
object ScreenTypeSettings: ScreenType()
@Serializable
object ScreenTypePractice: ScreenType()
@Serializable
object ScreenTypeStats: ScreenType()