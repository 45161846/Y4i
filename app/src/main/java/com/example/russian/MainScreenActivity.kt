package com.example.russian

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.russian.MyEnumClasses.MyTimerMode
import com.example.russian.MyEnumClasses.SortType
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.ViewModelPackage.GameSettings
import com.example.russian.ViewModelPackage.MyGameViewModelImpl
import com.example.russian.ViewModelPackage.MyMainViewModelArch
import com.example.russian.ViewModelPackage.MyMainViewModelImpl
import com.example.russian.ViewModelPackage.MyViewModel
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

    private lateinit var new_viewmodel: MyMainViewModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application
        val map = HashMap<String, Int>()
        map[getString(R.string.narechia_file)] = TaskTopic().NARECHI9

        new_viewmodel = MyMainViewModelImpl(
            app,
            map
        )

        setContent{
            RussianTheme {
                MainScreen()
            }
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.statusBarColor = getColor(R.color.dark_background)
        window.navigationBarColor = getColor(R.color.dark_background_2)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun MainScreen(){
        navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBarBottom(navController = navController)
            },
            content = {
                MainScreenContent()
            }
        )
    }

    @Composable
    private fun MainScreenContent(){

        NavHost(navController = navController,
            startDestination = ScreenTypePractice,
            modifier = Modifier.fillMaxSize()
        ){
            composable<ScreenTypePractice> {
                new_viewmodel.clearRepository()
                ScreenPractice()
            }
            composable<ScreenTypeSettings> {
                new_viewmodel.clearRepository()
                ScreenSettings()
            }
            composable<ScreenTypeStats> {
                ScreenStats()
            }
        }

    }

    @Composable
    private fun ScreenStats(){
        new_viewmodel.setRepository()
        DrawScreenStats()
    }

    @Composable
    private fun DrawScreenStats(){
        var loading by remember{
            mutableStateOf(new_viewmodel.loadingProcessesAmount.value)
        }

        new_viewmodel.loadingProcessesAmount.observe(this){
            loading = it
        }
        if(loading!! == 0) {
            StatsScreenDrawer().DrawContentScreen(new_viewmodel.getAllWordsForStats())
        }else{
            StatsScreenDrawer().DrawLoading(loading!!)
        }
    }

    @Composable
    private fun ScreenSettings(){
        val backColor = colorResource(id = R.color.dark_background)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(color = Color.White,
                text = "Settings",
            )
        }
    }

    @Composable
    private fun ScreenPractice(){

        val backColor = colorResource(id = R.color.dark_background)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            NarechiaButton()
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
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(1)
        }
        val navColor = colorResource(id = R.color.dark_background_2)
        val backColor = colorResource(id = R.color.dark_background)
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
                    selected = selectedItemIndex == index,
                    onClick = {
                        if(selectedItemIndex != index) {
                            selectedItemIndex = index
                            navController.navigate(item.type_of_screen)
                        }
                    },
                    icon = {
                        val currentIcon = if(index == selectedItemIndex){
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
    private fun NarechiaButton(){

        Button(modifier = Modifier
            .padding(20.dp, 25.dp)
            .size(400.dp, 100.dp),

            onClick = {
            this.startActivity(Intent(this, GameActivity::class.java))
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
        MainScreen()
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