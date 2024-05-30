package com.example.russian.mainScreenPackage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.russian.R
import com.example.russian.gameClasses.GameActivity
import com.example.russian.mainScreenPackage.screenDrawers.DrawFloatingButton
import com.example.russian.mainScreenPackage.screenDrawers.DrawNavigationBarBottom
import com.example.russian.mainScreenPackage.screenDrawers.DrawPracticeContent
import com.example.russian.mainScreenPackage.screenDrawers.DrawSettingsContent
import com.example.russian.mainScreenPackage.screenDrawers.DrawTopBar
import com.example.russian.mainScreenPackage.screenDrawers.StatsScreenDrawer
import kotlinx.serialization.Serializable


data class BottomNavigationItem(
    val type_of_screen: ScreenType,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector
)

class MainScreenActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    private lateinit var viewmodel: MyMainViewModelImpl


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

        var index by remember {
            mutableIntStateOf(viewmodel.selectedScreenIndex.value ?: 1)
        }

        viewmodel.selectedScreenIndex.observe(this){
            index = it ?: 1
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {

                DrawNavigationBarBottom(selectedItemIndex = index) {
                    viewmodel.selectedScreenIndex.value = it
                }
            },
            content = { innerPadding ->
                MainScreenContent(innerPadding)
            },
            topBar = {
                DrawTopBar(
                    selectedItemIndex = index,
                    window = window,
                    context = this,
                    onSearch = {
                        viewmodel.search(it)
                    },
                    onClear = {
                        viewmodel.resetCurrentWords()
                    }
                )
            },
            floatingActionButton = {
                DrawFloatingButton(index)
            }
        )
    }

    @Composable
    private fun MainScreenContent(paddingValues: PaddingValues) {

        var index by remember {
            mutableStateOf(viewmodel.selectedScreenIndex.value)
        }
        
        viewmodel.selectedScreenIndex.observe(this){
            index = it
        }
        
        when(index){
            0 -> DrawSettingsContent(paddingValues = paddingValues)
            1 -> DrawPracticeContent(paddingValues = paddingValues, startGame = {startGame(it)})
            2 -> ScreenStats(paddingValues = paddingValues)
        }

    }



    @SuppressLint("MutableCollectionMutableState")
    @Composable
    private fun ScreenStats(paddingValues: PaddingValues) {

        var itemsOnScreen by remember {
            mutableStateOf(viewmodel.repository.currentWords.value)
        }

        viewmodel.repository.currentWords.observe(this){
            itemsOnScreen = it
        }

        StatsScreenDrawer(paddingValues)
            .DrawContentScreen(listOfWords = itemsOnScreen ?: emptyList())
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